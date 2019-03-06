package com.yundingweibo.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class JDBCUtils {
    private static DataSource ds;

    static {

	   try {
		  //加载配置文件
		  Properties pro = new Properties();
		  //使用calssloder加载配置文件
		  pro.load(JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties"));

		  //初始化连接池对象
		  ds = DruidDataSourceFactory.createDataSource(pro);
	   } catch (Exception e) {
		  e.printStackTrace();
	   }

    }

    /**
	* 获取连接池对象
	*/

    public static DataSource getDataSource(){
        return ds;
    }

    /**
	* 获取connection对象
	*/

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

}
