package com.yundingweibo.dao.impl;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.yundingweibo.dao.DaoException;
import com.yundingweibo.dao.DaoFactory;
import com.yundingweibo.dao.UserDao;
import com.yundingweibo.dao.WeiboDao;
import com.yundingweibo.dao.impl.daoutil.DaoUtil;
import com.yundingweibo.domain.Comment;
import com.yundingweibo.domain.PageBean;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 这个类其实写的不好，dao层的逻辑太多了，但是现在已经没法改了
 *
 * @author 杜奕明
 * @date 2019/2/16 20:25
 */
public class JdbcWeiboDaoImpl implements WeiboDao {
    private UserDao userDao = DaoFactory.getUserDao();

    /**
     * 根据id查询微博
     *
     * @param weiboId 微博id
     * @return 封装好的Weibo对象
     */
    @Override
    public Weibo getWeibo(int weiboId) {
        String sql = "select * from weibo_data where weibo_id = ?";
        Weibo weibo = DaoUtil.toBean(Weibo.class, sql, weiboId).get(0);
        addCommentToBean(weibo);
        weibo.setNickname(userDao.getUserNickname(weibo.getUserId()));
        return weibo;
    }

    public void addCommentToBean(Weibo weibo) {
        List<Comment> comment = getTree(weibo.getWeiboId());
        for (Comment c : comment) {
            c.setNickname(userDao.getUserNickname(c.getUserId()));
            c.setProfilePicture(userDao.getUser(c.getUserId()).getProfilePicture());
            c.setCommentPraise(getCommentPraiseNum(c));
        }
        comment.sort((o1, o2) -> o2.getCommentTime().compareTo(o1.getCommentTime()));

        weibo.setCommentNum(comment.size());
        weibo.setComments(comment);
    }

    private static int floor = 1;

    private List<Comment> getTree(int weiboId) {
        String sql = "select * from weibo_comment where weibo_id=? and parent=0";
        List<Comment> treeList = DaoUtil.toBean(Comment.class, sql, weiboId);
        for (Comment root : treeList) {
            sql = "select count(*) from comment_praise where comment_id=?";
            DaoUtil.getObject(sql, root.getCommentId());
            addTree(root, weiboId);
            floor = 1;
        }
        return treeList;
    }

    private void addTree(Comment parent, int weiboId) {
        //查找parent的下一级评论
        List<Comment> childComment = this.findCommentByParentId(parent.getCommentId(), weiboId);
        if (childComment != null && childComment.size() > 0) {
            if (parent.getChildren() == null) {
                parent.setChildren(new ArrayList<>());
            }
            for (Comment temp : childComment) {
                User user = userDao.getUser(temp.getUserId());
                temp.setProfilePicture(user.getProfilePicture());
                temp.setNickname(user.getNickname());
                temp.setFloor(floor);
                DaoUtil.query("update weibo_comment set floor=? where comment_id=?", floor, temp.getCommentId());
                floor++;
                int num = getCommentPraiseNum(temp);
                temp.setCommentPraise(num);
                parent.getChildren().add(temp);
                addTree(temp, weiboId);
                Long object = (Long) DaoUtil.getObject("select floor from weibo_comment where comment_id=?", temp.getParent());
                if (object != null) {
                    temp.setParentFloor(object.intValue());
                }
            }
        }
    }

    private int getCommentPraiseNum(Comment temp) {
        String sql = "select count(*) from comment_praise where comment_id=?";
        Long object = (Long) DaoUtil.getObject(sql, temp.getCommentId());
        int num = 0;
        if (object != null) {
            num = object.intValue();
        }
        return num;
    }

    /**
     * 用于多级评论
     *
     * @param parentId 上层评论
     * @param weiboId  被评论的微博
     * @return 下层评论
     */
    @Override
    public List<Comment> findCommentByParentId(int parentId, int weiboId) {
        String sql = "select * from weibo_comment where weibo_id=? and parent=?";
        return DaoUtil.toBean(Comment.class, sql, weiboId, parentId);
    }

    /**
     * 根据用户id查询微博
     *
     * @param userId 用户id
     * @return 用户id对应的所有微博
     */
    @Override
    public PageBean<Weibo> getWeiboByUserId(int userId, int pageCode, int pageSize) {
        try {
            /*
             * 1. 设置PageBean对象pageBean
             * 2. 设置pb的pageCode和pageSize
             * 3. 得到totalRecord，设置给pageBean
             * 4. 得到beanList，设置给pageBean
             * 5. 返回pageBean
             */
            PageBean<Weibo> pageBean = new PageBean<>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            /*
             * 得到totalRecord
             */
            String sql = "select count(*) from weibo_data where user_id = ? order by create_time desc";
            int totalRecord;
            Object o = DaoUtil.getObject(sql, userId, userId);
            if (o == null) {
                totalRecord = 0;
            } else {
                totalRecord = ((Long) o).intValue();
            }

            pageBean.setTotalRecord(totalRecord);
            /*
             * 得到beanList
             */
            sql = "select * from weibo_data where user_id = ? order by create_time desc limit ?,?";
            List<Weibo> beanList = DaoUtil.toBean(Weibo.class, sql, userId, (pageCode - 1) * pageSize, pageSize);
            return addCommentsAndNicknameAndProfile(pageBean, beanList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 分页显示微博，查找的是user关注的用户的微博和自己的微博（包括自己转发的），微博按创建时间排序
     *
     * @param user     .
     * @param pageCode 页码
     * @param pageSize 一页显示几条记录
     * @return .
     */
    @Override
    public PageBean<Weibo> findAll(User user, int pageCode, int pageSize) {
        //select * from weibo_data where user_id in (select target_id from user_relation where user_id=? and type=1) order by create_time desc limit ?,?;
        try {
            /*
             * 1. 设置PageBean对象pageBean
             * 2. 设置pb的pageCode和pageSize
             * 3. 得到totalRecord，设置给pageBean
             * 4. 得到beanList，设置给pageBean
             * 5. 返回pageBean
             */
            PageBean<Weibo> pageBean = new PageBean<>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            /*
             * 得到totalRecord
             */
            String sql = "select count(*) from weibo_data where user_id in (select target_id from user_relation where user_id=? and type=1) or user_id=?";
            int totalRecord;
            Object o = DaoUtil.getObject(sql, user.getUserId(), user.getUserId());
            if (o == null) {
                totalRecord = 0;
            } else {
                totalRecord = ((Long) o).intValue();
            }

            pageBean.setTotalRecord(totalRecord);
            /*
             * 得到beanList
             */
            sql = "select * from weibo_data where user_id in (select target_id from user_relation where user_id=? and type=1) or user_id=? order by create_time desc limit ?,?";
            List<Weibo> beanList = DaoUtil.toBean(Weibo.class, sql, user.getUserId(), user.getUserId(), (pageCode - 1) * pageSize, pageSize);
            return addCommentsAndNicknameAndProfile(pageBean, beanList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 分页显示我的点赞
     *
     * @param user     .
     * @param pageCode .
     * @param pageSize .
     * @return .
     */
    @Override
    public PageBean<Weibo> findPraise(User user, int pageCode, int pageSize) {
        try {
            /*
             * 1. 设置PageBean对象pageBean
             * 2. 设置pb的pageCode和pageSize
             * 3. 得到totalRecord，设置给pageBean
             * 4. 得到beanList，设置给pageBean
             * 5. 返回pageBean
             */
            PageBean<Weibo> pageBean = new PageBean<>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            /*
             * 得到totalRecord
             */
            String sql = "select count(*) from weibo_data where weibo_id in (select weibo_id from weibo_praise where user_id=?)";
            int totalRecord;
            Object o = DaoUtil.getObject(sql, user.getUserId());
            if (o == null) {
                totalRecord = 0;
            } else {
                totalRecord = ((Long) o).intValue();
            }

            pageBean.setTotalRecord(totalRecord);
            /*
             * 得到beanList
             */
            sql = "select * from weibo_data where weibo_id in (select weibo_id from weibo_praise where user_id=?) limit ?,?";
            List<Weibo> beanList = DaoUtil.toBean(Weibo.class, sql, user.getUserId(), (pageCode - 1) * pageSize, pageSize);
            return addCommentsAndNicknameAndProfile(pageBean, beanList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Weibo> getPraiseList() {
        String sql = "select weibo_content,praise_num from weibo_data order by praise_num desc limit 25";
        return DaoUtil.toBean(Weibo.class, sql);
    }

    @Override
    public List<Weibo> getRepostList() {
        String sql = "select weibo_content,repost_num from weibo_data order by repost_num desc limit 25";
        return DaoUtil.toBean(Weibo.class, sql);
    }

    /**
     * 根据发过来的weibo的weiboId查找本微博的所有直接评论，包括评论的回复
     *
     * @param weibo .
     * @return .
     */
    @Override
    public List<Comment> getComment(Weibo weibo) {
        return getTree(weibo.getWeiboId());
    }

    /**
     * 微博点赞，如果点过赞了就取消点赞
     *
     * @param weiboId 要点赞的微博id
     * @param user    sessionUser
     * @return 返回0是取消点赞，返回1是添加点赞
     */
    @Override
    public int like(int weiboId, User user) {
        String sql = "select user_id from weibo_praise where weibo_id=? and user_id=?";
        Long userId;
        try {
            userId = (Long) DaoUtil.getObject(sql, weiboId, user.getUserId());
        } catch (Exception e) {
            //基本不可能执行到这里，实在想不出来这个DaoUtil.toBeanSingle可能会出什么问题
            throw new RuntimeException("发生未知错误");
        }
        //事务专用连接
        DruidPooledConnection connection;

        //如果点过赞就取消点赞，取消点赞之后直接终止函数执行
        if (userId != null) {
            try {
                connection = DaoUtil.beginTransaction();
                sql = "delete from weibo_praise where user_id=? and weibo_id=?";
                DaoUtil.query(connection, sql, user.getUserId(), weiboId);
                sql = "update weibo_data set praise_num=praise_num-1 where weibo_id=?";
                DaoUtil.query(connection, sql, weiboId);
                DaoUtil.commitTransaction();
                return 0;
            } catch (Exception e) {
                try {
                    DaoUtil.rollbackTransaction();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                throw new RuntimeException("取消点赞出错");
            }
        }

        try {
            connection = DaoUtil.beginTransaction();
            sql = "insert into weibo_praise(user_id,weibo_id,praise_time) values(?,?,now())";
            DaoUtil.query(connection, sql, user.getUserId(), weiboId);
            sql = "update weibo_data set praise_num=praise_num+1 where weibo_id=?";
            DaoUtil.query(connection, sql, weiboId);
            DaoUtil.commitTransaction();
            return 1;
        } catch (Exception e) {
            try {
                DaoUtil.rollbackTransaction();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException("点赞出错");
        }
    }

    /**
     * 评论点赞
     *
     * @param comment 要点赞的评论
     * @return 返回0是取消点赞，返回1是添加点赞
     */
    @Override
    public int like(Comment comment, User user) {
        String sql = "select user_id from comment_praise where user_id=? and comment_id=?";
        Comment comment1;
        try {
            comment1 = DaoUtil.toBeanSingle(Comment.class, sql, user.getUserId(), comment.getCommentId());
        } catch (Exception e) {
            //基本不可能执行到这里，实在想不出来这个DaoUtil.toBeanSingle可能会出什么问题
            throw new RuntimeException("发生未知错误");
        }

        if (comment1 != null) {
            try {
                sql = "delete from comment_praise where user_id=? and comment_id=?";
                DaoUtil.query(sql, user.getUserId(), comment.getCommentId());
                return 0;
            } catch (Exception e) {
                throw new RuntimeException("点赞出错");
            }
        }

        try {
            sql = "insert into comment_praise(user_id,comment_id) values(?,?)";
            DaoUtil.query(sql, user.getUserId(), comment.getCommentId());
            return 1;
        } catch (Exception e) {
            throw new RuntimeException("点赞出错");
        }
    }

    /**
     * 添加微博的评论，需要weiboId,commentId,userId
     * 添加的是一级评论，即直接评论微博
     *
     * @param weibo   .
     * @param comment .
     * @param user    .
     */
    @Override
    public void addComment(Weibo weibo, Comment comment, User user) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(comment.getCommentTime());
        String sql = "insert into weibo_comment(comment_content,comment_time,user_id,weibo_id,parent) values(?,?,?,?,0)";
        DaoUtil.query(sql, comment.getCommentContent(), format, user.getUserId(), weibo.getWeiboId());
    }

    /**
     * 获取commentId对应的Comment
     *
     * @param comment .
     * @return .
     */
    @Override
    public Comment findComment(Comment comment) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(comment.getCommentTime());
        String sql = "select * from weibo_comment where comment_time=? and user_id";
        Comment comment1 = DaoUtil.toBeanSingle(Comment.class, sql, format, comment.getUserId());
        if (comment1 != null) {
            comment1.setNickname(userDao.getUserNickname(comment1.getUserId()));
        }
        return comment1;
    }

    /**
     * 给评论添加回复
     *
     * @param comment .
     * @param user    sessionUser
     */
    @Override
    public void addReply(Comment comment, Comment replyComment, User user) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(replyComment.getCommentTime());
        String sql = "insert into weibo_comment(comment_content,comment_time,user_id,weibo_id,parent,floor) values(?,?,?,?,?,?)";
        DaoUtil.query(sql, replyComment.getCommentContent(),
                format, user.getUserId(), comment.getWeiboId(), comment.getCommentId(), replyComment.getFloor());
        Weibo w = new Weibo();
        w.setWeiboId(comment.getWeiboId());
        this.addCommentToBean(w);
    }

    @Override
    public void deleteRepost(User user, Weibo weibo) {
        String sql = "delete from weibo_repost where user_id=? and weibo_id=?";
        DaoUtil.query(sql, user.getUserId(), weibo.getWeiboId());
    }

    @Override
    public void addRepost(User user, Weibo weibo) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());
        DruidPooledConnection connection;
        try {
            connection = DaoUtil.beginTransaction();
            //插入转发表
            String sql = "insert into weibo_repost(user_id,weibo_id,repost_time) values(?,?,?)";
            DaoUtil.query(connection, sql, user.getUserId(), weibo.getWeiboId(), format);

            /*
             * 因为查出来的photo已经被封装成了List，插入数据库时要求每个url之间用逗号隔开，所以我们还需要遍历List，把photo拼成一个字符串
             * 经测试，上面写的方法比查询两次慢，所以这里使用两次查询，即第一次查微博内容，第二次查图片url
             * */
            sql = "select weibo_content from weibo_data where weibo_id=?";
            Weibo weibo1 = DaoUtil.toBeanSingle(Weibo.class, sql, weibo.getWeiboId());
            if (weibo1 == null) {
                throw new DaoException("找不到要转发的微博");
            }
            sql = "select photo from weibo_data where weibo_id=?";
            String photo = (String) DaoUtil.getObject(sql, weibo.getWeiboId());

            sql = "insert into weibo_data(weibo_content,photo,is_origin,user_id,create_time) values(?,?,false,?,?)";
            DaoUtil.query(connection, sql, weibo1.getWeiboContent(), photo, user.getUserId(), format);

            sql = "update weibo_data set repost_num = repost_num + 1 where weibo_id=?";
            DaoUtil.query(connection, sql, weibo.getWeiboId());

            sql = "update user_info set weibo_num = weibo_num +1 where user_id = ?";
            DaoUtil.query(connection, sql, user.getUserId());
            DaoUtil.commitTransaction();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            try {
                DaoUtil.rollbackTransaction();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException("转发失败");
        }
    }

    @Override
    public List<Weibo> getRepost(User user) {
        String sql = "select * from weibo_data where weibo_id in (select weibo_id from weibo_repost where user_id=? order by repost_time desc)";
        return DaoUtil.toBean(Weibo.class, sql, user.getUserId());
    }

    /**
     * 显示我发出的评论
     *
     * @param user .
     * @return .
     */
    @Override
    public List<Comment> showCommentSend(User user) {
        String sql = "select * from weibo_comment where user_id=? order by comment_time desc";
        return DaoUtil.toBean(Comment.class, sql, user.getUserId());
    }

    /**
     * 显示我收到的评论
     *
     * @param user .
     * @return .
     */
    @Override
    public List<Comment> showCommentReceive(User user) {
        String sql = "select * from weibo_comment where weibo_id in (select weibo_id from weibo_data where user_id=?) order by comment_time desc";
        List<Comment> comments = DaoUtil.toBean(Comment.class, sql, user.getUserId());
        List<Comment> all = new ArrayList<>(comments);
        for (Comment comment : comments) {
            setProfilePicture(comment);
        }
        return all;
    }

    private boolean setProfilePicture(Comment comment) {
        int userId = comment.getUserId();
        if (userId < 0) {
            return true;
        }
        User user1 = userDao.getUser(userId);
        if (user1 == null) {
            return true;
        }
        comment.setProfilePicture(user1.getProfilePicture());
        return false;
    }

    /**
     * .
     *
     * @param user  .
     * @param weibo .
     */
    @Override
    public int addWeibo(User user, Weibo weibo) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());

        String sql = "insert into weibo_data(weibo_content,user_id,create_time,photo) values(?,?,?,?)";
        DaoUtil.query(sql, weibo.getWeiboContent(), user.getUserId(), format, weibo.getPhoto().get(0));
        sql = "update user_info set weibo_num = weibo_num+1 where user_id = ?";
        DaoUtil.query(sql, user.getUserId());
        sql = "select weibo_id from weibo_data where create_time=? and user_id=?";
        Long weiboId = (Long) DaoUtil.getObject(sql, format, user.getUserId());
        return weiboId != null ? weiboId.intValue() : 0;
    }

    /**
     * 用于随机展示微博
     *
     * @return .
     */
    @Override
    public PageBean<Weibo> showAll(int pageCode, int pageSize) {
        try {
            /*
             * 1. 设置PageBean对象pageBean
             * 2. 设置pb的pageCode和pageSize
             * 3. 得到totalRecord，设置给pageBean
             * 4. 得到beanList，设置给pageBean
             * 5. 返回pageBean
             */
            PageBean<Weibo> pageBean = new PageBean<>();
            pageBean.setPageCode(pageCode);
            pageBean.setPageSize(pageSize);
            /*
             * 得到totalRecord
             */
            String sql = "select count(*) from weibo_data";
            int totalRecord;
            Object o = DaoUtil.getObject(sql);
            if (o == null) {
                totalRecord = 0;
            } else {
                totalRecord = ((Long) o).intValue();
            }

            pageBean.setTotalRecord(totalRecord);
            /*
             * 得到beanList
             */
            sql = "select * from weibo_data order by create_time desc limit ?,?";
            List<Weibo> beanList = DaoUtil.toBean(Weibo.class, sql, (pageCode - 1) * pageSize, pageSize);
            return addCommentsAndNicknameAndProfile(pageBean, beanList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PageBean<Weibo> addCommentsAndNicknameAndProfile(PageBean<Weibo> pageBean, List<Weibo> beanList) {
        for (Weibo w : beanList) {
            addCommentToBean(w);
            w.setNickname(userDao.getUserNickname(w.getUserId()));
            User user1 = userDao.getUser(w.getUserId());
            w.setProfilePicture(user1.getProfilePicture());
        }
        pageBean.setBeanList(beanList);
        return pageBean;
    }

    /**
     * 根据commnetId查找评论
     *
     * @param comment .
     * @return .
     */
    @Override
    public Comment findCommentByCommentId(Comment comment) {
        String sql = "select * from weibo_comment where comment_id =?";
        return DaoUtil.toBeanSingle(Comment.class, sql, comment.getCommentId());
    }
}
