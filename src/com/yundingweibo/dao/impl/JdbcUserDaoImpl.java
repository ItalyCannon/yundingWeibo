package com.yundingweibo.dao.impl;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.yundingweibo.dao.DaoException;
import com.yundingweibo.dao.DaoFactory;
import com.yundingweibo.dao.UserDao;
import com.yundingweibo.dao.impl.daoutil.DaoUtil;
import com.yundingweibo.domain.Comment;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 杜奕明
 * @date 2019/2/15 11:23
 */
public class JdbcUserDaoImpl implements UserDao {
    /**
     * 根据id查询用户
     *
     * @param userId 用户id
     * @return 封装好的User对象
     */
    @Override
    public User getUser(int userId) {
        String sql = "select * from user_info where user_id = ?";
        return DaoUtil.toBeanSingle(User.class, sql, userId);
    }

    /**
     * 把传来的User对象写入数据库的user_info表
     *
     * @param user User对象
     */
    @Override
    public void update(User user) {
        try {
            DaoUtil.set(user);
        } catch (DaoException e) {
            throw new DaoException("sql写入失败");
        }
    }

    @Override
    public User getUserByLoginId(String loginId) {
        String sql = "select * from user_info where login_id = ?";
        List<User> list = DaoUtil.toBean(User.class, sql, loginId);
        if (list.size() == 0) {
            return null;
        } else {
            return list.get(0);
        }
    }

    @Override
    public String getUserNickname(int userId) {
        String sql = "select nickname from user_info where user_id = ?";
        String nickname = (String) DaoUtil.getObject(sql, userId);
        if (nickname == null) {
            return "用户" + userId;
        }
        return nickname;
    }

    @Override
    public void addCollection(User user, Weibo weibo) {
        String sql = "insert into user_collection (user_id,weibo_id,collection_time) values(?,?,now())";
        DaoUtil.query(sql, user.getUserId(), weibo.getWeiboId());
    }

    @Override
    public void removeCollection(User user, Weibo weibo) {
        String sql = "delete from user_collection where user_id=? and weibo_id=?";
        DaoUtil.query(sql, user.getUserId(), weibo.getWeiboId());
    }

    @Override
    public List<Weibo> showUserCollections(User user) {
        String sql = "select * from weibo_data where weibo_id in (select weibo_id from user_collection where user_id=?) order by create_time desc";
        List<Weibo> list = DaoUtil.toBean(Weibo.class, sql, user.getUserId());
        for (Weibo w : list) {
            List<Comment> comment = DaoFactory.getWeiboDao().getComment(w);
            for (Comment c : comment) {
                c.setProfilePicture(this.getUser(c.getUserId()).getProfilePicture());
            }
            w.setComments(comment);
            w.setCommentNum(comment.size());
            w.setNickname(this.getUserNickname(w.getUserId()));
        }
        return list;
    }

    @Override
    public List<User> showUserAttention(User user) {
        String sql = "select target_id as `user_id`,attention_time,attention_group from user_relation where user_id=? order by attention_time desc";
        List<User> list = DaoUtil.toBean(User.class, sql, user.getUserId());
        List<User> userList = new ArrayList<>();
        int i = 0;
        for (User u : list) {
            sql = "select user_id,nickname,signature,profile_picture from user_info where user_id=?";
            u = DaoUtil.toBeanSingle(User.class, sql, list.get(i).getUserId());
            if (u != null) {
                u.setAttentionGroup(list.get(i).getAttentionGroup());
            }
            i++;
            userList.add(u);
        }
        return userList;
    }

    /**
     * 查询user关注的粉丝
     *
     * @param user .
     * @return .
     */
    @Override
    public List<User> showFans(User user) {
        String sql = "select user_id,attention_time,attention_group from user_relation where target_id=? order by attention_time desc";
        List<User> list = DaoUtil.toBean(User.class, sql, user.getUserId());
        List<User> userList = new ArrayList<>();
        int i = 0;
        for (User u : list) {
            sql = "select user_id,nickname,signature,profile_picture from user_info where user_id=?";
            u = DaoUtil.toBeanSingle(User.class, sql, list.get(i).getUserId());
            userList.add(u);
            i++;
        }
        return userList;
    }

    /**
     * 根据loginId查询用户
     * 查询的是login_info表
     *
     * @param registerId .
     * @return .
     */
    @Override
    public User findUserByLoginInId(String registerId) {
        String sql = "select * from login_info where login_id=?";
        return DaoUtil.toBeanSingle(User.class, sql, registerId);
    }

    /**
     * 把registerUser的loginId和userId同时写入login_info和user_info表
     *
     * @param registerUser .
     */
    @Override
    public void addUser(User registerUser) {
        /*
         * 这么写的原因：
         *  首先，最初的设计使用了事务，正常情况下，login_info和user_info表的user_id字段肯定是一一对应的，但是仔细
         *  想的话会有下面的问题
         *  因为是同时写入两个表，如果成功把数据插入login_info表，但是在插入user_info表时出错，虽然事务会回滚，数据
         *  也没有写入表中，但是login_info表的user_id已经自动增长了1（因为是先插入login_info表），下次再插入时就会
         *  出现两个表的user_id对不上的情况，所以我想出了一个解决办法：插入login_id表之后就拿到对应的user_id，然后把
         *  拿到的user_id和页面传过来的login_id一块插到user_info表中，但是因为整个操作都在事务中，所以我只能允许读脏
         *  数据这以操作，但是不知道为啥即使我设置了隔离级别，他也读不了脏数据
         *
         * 所以此处使用极端办法：如果注册出错那么我们就直接把刚刚插入数据库的数据全部删除，相当于是做了个手动回滚
         * @author 杜奕明
         * @date 2019/3/2 19:52
         * */
        try {
            String sql = "insert into login_info set login_id=?,password=?";
            DaoUtil.query(sql, registerUser.getLoginId(), registerUser.getPassword());
            sql = "select user_id from login_info where login_id=?";
            Long id = (Long) DaoUtil.getObject(sql, registerUser.getLoginId());
            if (id != null) {
                int userId = id.intValue();
                sql = "insert into user_info(user_id,login_id,registration_time,nickname,profile_picture,background,real_name,birthday) values(?,?,now(),?,?,?,?,?)";
                DaoUtil.query(sql, userId, registerUser.getLoginId(), "用户" + id, registerUser.getProfilePicture(),
                        registerUser.getBackground(), registerUser.getRealName(), registerUser.getBirthday());
            } else {
                throw new RuntimeException("也不知道是什么错误，请检查JdbcUserDaoImpl的addUser方法，错因是在login_info表中找不到当前用户的login_id");
            }
        } catch (Exception e) {
            String sql = "delete from user_info where login_id=?";
            DaoUtil.query(sql, registerUser.getLoginId());
            sql = "delete from login_info where login_id=?";
            DaoUtil.query(sql, registerUser.getLoginId());
            throw new RuntimeException("写入数据库失败");
        }
    }

    /**
     * sessionUser取关removeUser
     *
     * @param sessionUser .
     * @param removeUser  .
     */
    @Override
    public void removeAttention(User sessionUser, User removeUser) {
        DruidPooledConnection connection;
        try {
            connection = DaoUtil.beginTransaction();
            String sql = "delete from user_relation where user_id=? and target_id=?";
            DaoUtil.query(connection, sql, sessionUser.getUserId(), removeUser.getUserId());

            sql = "update user_info set subscribe_num=subscribe_num-1 where user_id=?";
            DaoUtil.query(connection, sql, sessionUser.getUserId());

            sql = "update user_info set fans_num=fans_num-1 where user_id=?";
            DaoUtil.query(connection, sql, removeUser.getUserId());
            DaoUtil.commitTransaction();
        } catch (Exception e) {
            try {
                DaoUtil.rollbackTransaction();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException("写入数据库失败");
        }
    }

    /**
     * sessionUser关注targetUser
     *
     * @param sessionUser .
     * @param targetUser  .
     */
    @Override
    public void addAttention(User sessionUser, User targetUser) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(new Date());
        DruidPooledConnection connection;
        try {
            connection = DaoUtil.beginTransaction();
            String sql = "insert into user_relation set user_id=?,target_id=?,type=1,attention_time=?";
            DaoUtil.query(connection, sql, sessionUser.getUserId(), targetUser.getUserId(), format);

            sql = "update user_info set subscribe_num = subscribe_num+1 where user_id=?";
            DaoUtil.query(connection, sql, sessionUser.getUserId());

            sql = "update user_info set fans_num = fans_num+1 where user_id=?";
            DaoUtil.query(connection, sql, targetUser.getUserId());
            DaoUtil.commitTransaction();
        } catch (Exception e) {
            try {
                DaoUtil.rollbackTransaction();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException("写入数据库失败");
        }
    }

    /**
     * 显示user的所有粉丝
     *
     * @param user .
     * @return .
     */
    @Override
    public Integer showFansNum(User user) {
        String sql = "select count(*) from user_relation where target_id=?";
        Long i = (Long) DaoUtil.getObject(sql, user.getUserId());
        if (i == null) {
            return 1;
        } else {
            return i.intValue();
        }
    }

    /**
     * 在我的关注页面的左上角显示个人数据
     *
     * @param user .
     * @return .
     */
    @Override
    public User showBasicInfo(User user) {
        String sql = "select nickname,signature,profile_picture,fans_num,weibo_num,subscribe_num from user_info where user_id=?";
        User user1 = DaoUtil.toBeanSingle(User.class, sql, user.getUserId());
        if (user1 != null) {
            user1.setUserId(user.getUserId());
        }
        return user1;
    }

    /**
     * @param sessionUser .
     */
    @Override
    public void queryBackground(User sessionUser) {
        String sql = "update user_info set background=? where user_id=?";
        DaoUtil.query(sql, sessionUser.getBackground(), sessionUser.getUserId());
    }

    /**
     * 查询sessionUser的背景图
     *
     * @param sessionUser .
     * @return .
     */
    @Override
    public String findBackgroundURL(User sessionUser) {
        String sql = "select background from user_info where user_id=?";
        String object = (String) DaoUtil.getObject(sql, sessionUser.getUserId());
        if (object != null) {
            return object;
        }
        return "";
    }
}