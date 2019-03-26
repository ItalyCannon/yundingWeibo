package com.yundingweibo.dao.impl.daoutil;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.yundingweibo.domain.User;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * 如果有时间的话可以把这个工具类换掉
 * <p>
 * 切换方法：如果想要换成Mybatis的话就直接把dao层整个换掉，然后在daoFactoryConfig.properties里把实现类的名字改了
 * <p>
 * 此工具以Druid连接池为基础，可以实现对jdbc的部分简化，但我对里面的算法不太满意，有时间的话可以把这个类换掉
 *
 * @author 杜奕明
 * @date 2019/2/15 11:24
 */
public class DaoUtil {
    /**
     * 连接池
     */
    private static DruidDataSource dataSource;

    private static ThreadLocal<DruidPooledConnection> threadLocal = new ThreadLocal<>();

    /*
     * 加载配置文件
     */
    static {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            inputStream = DaoUtil.class.getClassLoader()
                    .getResourceAsStream("com/yundingweibo/dao/impl/daoutil/dbConfig.properties");
            prop.load(inputStream);
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(prop);
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e.getMessage());
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 开启事务，一个线程只能同时开启一个事务
     *
     * @return 事务专用连接
     * @throws SQLException 已经开启了一个事务
     */
    public static DruidPooledConnection beginTransaction() throws SQLException {
        DruidPooledConnection connection = threadLocal.get();
        if (connection != null) {
            throw new SQLException("事务已经开启");
        }
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        threadLocal.set(connection);
        return connection;
    }

    /**
     * 提交事务
     *
     * @throws SQLException 还没开启事务，不能提交
     */
    public static void commitTransaction() throws SQLException {
        DruidPooledConnection connection = threadLocal.get();
        if (connection == null) {
            throw new SQLException("还没开启事务，不能提交");
        }
        connection.commit();
        connection.recycle();
        threadLocal.remove();
    }

    public static void rollbackTransaction() throws SQLException {
        DruidPooledConnection connection = threadLocal.get();
        if (connection == null) {
            throw new SQLException("还没开启事务，不能回滚");
        }
        connection.rollback();
        connection.recycle();
        threadLocal.remove();
    }

    /**
     * 事务用这个方法
     *
     * @param conn   事务专用连接
     * @param clazz  .
     * @param sql    .
     * @param params .
     * @param <T>    .
     * @return .
     */
    public static <T> List<T> toBean(DruidPooledConnection conn, Class<T> clazz, String sql, Object... params) {
        List<T> list = new ArrayList<>();

        try {
            PreparedStatement ps = conn.prepareStatement(sql);

            /* 替换问号占位符 */
            if (params.length != 0) {
                replace(ps, params);
            }
            ResultSet rs = ps.executeQuery();

            /* 获取结果集里的所有字段名 */
            String[] colNames = getColNames(rs);

            /*
             * 自动执行set方法
             */
            try {
                Method[] ms = clazz.getMethods();
                while (rs.next()) {
                    T object = clazz.newInstance();
                    for (String colName : colNames) {
                        String methodName = "set" + underlineToHump(colName);
                        callMethod(rs, ms, object, colName, methodName);
                    }
                    list.add(object);
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e.getMessage());
            } finally {
                rs.close();
                ps.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    /**
     * 执行sql语句，返回一个封装好的对象集合
     *
     * @param clazz  期望得到的类型，比如如果想要得到一个String类的返回结果就传入String.class
     * @param sql    要执行的sql语句
     * @param params prepareStatement的问号占位符
     * @return clazz.class对应的类型
     * @throws RuntimeException clazz类中的属性不是int,double,float,String,boolean,java.util,Date或其装箱类时抛出
     */
    public static <T> List<T> toBean(Class<T> clazz, String sql, Object... params) {
        try {
            DruidPooledConnection connection = dataSource.getConnection();
            List<T> bean = toBean(connection, clazz, sql, params);
            connection.recycle();
            return bean;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toBeanSingle(Class<T> clazz, String sql, Object... params) {
        try {
            DruidPooledConnection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            /* 替换问号占位符 */
            replace(ps, params);

            ResultSet rs = ps.executeQuery();

            /* 获取结果集里的所有字段名 */
            String[] colNames = getColNames(rs);

            /*
             * 自动执行set方法
             */
            try {
                Method[] ms = clazz.getMethods();
                if (rs.next()) {
                    T object = clazz.newInstance();
                    for (String colName : colNames) {
                        String methodName = "set" + underlineToHump(colName);
                        callMethod(rs, ms, object, colName, methodName);
                    }
                    return object;
                }
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            } finally {
                rs.close();
                ps.close();
                conn.recycle();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Object getObject(String sql, Object... params) {
        DruidPooledConnection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            /* 替换问号占位符 */
            replace(ps, params);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getObject(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                free(conn, ps, rs);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 事务专用提交
     *
     * @param connection .
     * @param sql        .
     * @param params     .
     */
    public static void query(DruidPooledConnection connection, String sql, Object... params) {
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);
            replace(ps, params);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                free(null, ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void query(String sql, Object... params) {
        DruidPooledConnection conn = null;
        PreparedStatement ps = null;
        try {
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(sql);

            /* 替换问号占位符 */
            replace(ps, params);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                free(conn, ps);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 把User对象里的所有非空内容都写入数据库
     *
     * @param u .
     */
    public static void set(User u) {
        DruidPooledConnection conn = null;
        Statement preparedStatement = null;
        ResultSet resultset = null;
        PreparedStatement pstmt = null;

        try {
            conn = dataSource.getConnection();

            //得到数据表的字段名
            preparedStatement = conn.createStatement();
            resultset = preparedStatement.executeQuery("select * from user_info where user_id = 1");
            String[] colNames = getColNames(resultset);

            for (String colName : colNames) {
                Method[] ms = User.class.getMethods();
                Object o = null;
                String methodName = "get" + underlineToHump(colName);
                for (Method m : ms) {
                    if (methodName.equals(m.getName())) {
                        o = m.invoke(u);
                        break;
                    }
                }
                if (o != null) {
                    pstmt = conn.prepareStatement("update user_info set " + colName + "=?" + " where user_id="
                            + u.getUserId());
                    pstmt.setObject(1, o);
                    pstmt.executeUpdate();
                }
            }
        } catch (IllegalAccessException | SQLException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                free(conn, preparedStatement, resultset, pstmt);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void free(DruidPooledConnection conn, Statement preparedStatement,
                             ResultSet resultset,
                             PreparedStatement pstmt) throws SQLException {
        if (pstmt != null) {
            pstmt.close();
        }

        free(conn, preparedStatement, resultset);
    }

    private static void free(DruidPooledConnection conn,
                             Statement preparedStatement,
                             ResultSet resultset) throws SQLException {

        if (resultset != null) {
            resultset.close();
        }

        if (preparedStatement != null) {
            preparedStatement.close();
        }

        if (conn != null) {
            conn.recycle();
        }
    }

    private static void free(DruidPooledConnection conn,
                             Statement preparedStatement) throws SQLException {
        if (preparedStatement != null) {
            preparedStatement.close();
        }

        if (conn != null) {
            conn.recycle();
        }
    }

    /**
     * 下划线命名转驼峰命名
     *
     * @param param .
     * @return .
     */
    private static String underlineToHump(String param) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (param == null || param.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!param.contains("_")) {
            // 不含下划线，仅将首字母大写
            return param.substring(0, 1).toUpperCase() + param.substring(1);
        }
        // 用下划线将原始字符串分割
        String[] camels = param.split("_");
        for (String camel : camels) {
            // 跳过原始字符串中开头、结尾的下换线或双重下划线
            if (camel.isEmpty()) {
                continue;
            }
            // 处理真正的驼峰片段
            if (result.length() == 0) {
                // 第一个驼峰片段，全部字母都小写
                result.append(camel.substring(0, 1).toUpperCase()).append(camel.substring(1));
            } else {
                // 其他的驼峰片段，首字母大写
                result.append(camel.substring(0, 1).toUpperCase());
                result.append(camel.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰转下划线
     *
     * @param para .
     * @return .
     */
    private static String humpToUnderline(String para) {
        StringBuilder sb = new StringBuilder(para);
        //定位
        int temp = 0;
        if (!para.contains("_")) {
            for (int i = 0; i < para.length(); i++) {
                if (Character.isUpperCase(para.charAt(i))) {
                    sb.insert(i + temp, "_");
                    temp += 1;
                }
            }
        }
        return sb.toString().toLowerCase();
    }

    /**
     * 获取结果集里的所有字段名
     *
     * @param rs .
     * @throws SQLException .
     */
    private static String[] getColNames(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int count = rsmd.getColumnCount();
        String[] colNames = new String[count];
        for (int i = 1; i <= count; i++) {
            colNames[i - 1] = rsmd.getColumnLabel(i);
        }
        return colNames;
    }

    private static <T> void callMethod(ResultSet rs, Method[] ms, T object, String colName, String methodName) throws IllegalAccessException, InvocationTargetException, SQLException {
        for (Method m : ms) {
            if (methodName.equals(m.getName())) {
                String type = m.getParameterTypes()[0].getName();
                switch (type) {
                    case "int":
                    case "java.lang.Integer":
                        m.invoke(object, rs.getInt(colName));
                        break;
                    case "java.lang.String":
                        m.invoke(object, rs.getString(colName));
                        break;
                    case "java.util.Date":
                        Timestamp timestamp = rs.getTimestamp(colName);
                        if (timestamp == null) {
                            break;
                        }
                        m.invoke(object, new java.util.Date(timestamp.getTime()));
                        break;
                    case "double":
                    case "java.lang.Double":
                        m.invoke(object, rs.getDouble(colName));
                        break;
                    case "float":
                    case "java.lang.Float":
                        m.invoke(object, rs.getFloat(colName));
                        break;
                    case "boolean":
                    case "java.lang.Boolean":
                        m.invoke(object, rs.getBoolean(colName));
                        break;
                    case "java.util.List":
                        String urls = rs.getString(colName);
                        if (urls == null) {
                            break;
                        }
                        String split = ",";
                        if (urls.contains(split)) {
                            String[] url = urls.split(split);
                            List<String> l = new ArrayList<>();
                            Collections.addAll(l, url);
                            m.invoke(object, l);
                            break;
                        }
                    default:
                        break;
                }
                break;
            }
        }
    }

    /**
     * 替换问号占位符
     *
     * @param ps     .
     * @param params .
     * @throws SQLException .
     */
    private static void replace(PreparedStatement ps, Object[] params) throws SQLException {
        ParameterMetaData pmd = ps.getParameterMetaData();
        int count = pmd.getParameterCount();
        for (int i = 1; i <= count; i++) {
            ps.setObject(i, params[i - 1]);
        }
    }
}
