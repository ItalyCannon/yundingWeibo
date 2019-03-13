package com.yundingweibo.dao.impl;

import com.yundingweibo.dao.DaoFactory;
import com.yundingweibo.dao.UserDao;
import com.yundingweibo.dao.WeiboDao;
import com.yundingweibo.dao.impl.daoutil.DaoUtil;
import com.yundingweibo.domain.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        addCommentsAndNickname(weibo, weibo.getUserId());
        return weibo;
    }

    private void addCommentsAndNickname(Weibo weibo, int userId) {
        List<Comment> comment = this.getComment(weibo);
        for (Comment c : comment) {
            c.setProfilePicture(userDao.getUser(c.getUserId()).getProfilePicture());
        }
        weibo.setCommentNum(comment.size());
        weibo.setComments(comment);
        weibo.setNickname(userDao.getUserNickname(userId));
    }

    /**
     * 根据用户id查询微博
     *
     * @param userId 用户id
     * @return 用户id对应的所有微博
     */
    @Override
    public List<Weibo> getWeiboByUserId(int userId) {
        String sql = "select * from weibo_data where user_id = ? order by create_time desc";
        List<Weibo> weiboList = DaoUtil.toBean(Weibo.class, sql, userId);
        for (Weibo w : weiboList) {
            addCommentsAndNickname(w, userId);
        }
        return weiboList;
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
            for (Weibo w : beanList) {
                addCommentsAndNickname(w, w.getUserId());
                User user1 = userDao.getUser(w.getUserId());
                w.setProfilePicture(user1.getProfilePicture());
            }
            pageBean.setBeanList(beanList);
            return pageBean;
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
            for (Weibo w : beanList) {
                addCommentsAndNickname(w, w.getUserId());
                User user1 = userDao.getUser(w.getUserId());
                w.setProfilePicture(user1.getProfilePicture());
            }
            pageBean.setBeanList(beanList);
            return pageBean;
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
        String sql = "select * from weibo_comment where weibo_id=? order by comment_time desc";
        List<Comment> comments = DaoUtil.toBean(Comment.class, sql, weibo.getWeiboId());
        sql = "select * from reply_comment where comment_id=? order by reply_time desc";
        for (Comment c : comments) {
            List<ReplyComment> replyComments = DaoUtil.toBean(ReplyComment.class, sql, c.getCommentId());
            for (ReplyComment r : replyComments) {
                r.setNickname(userDao.getUserNickname(r.getUserId()));
            }
            c.setReplyComments(replyComments);
            c.setNickname(userDao.getUserNickname(c.getUserId()));
        }
        return comments;
    }

    /**
     * 微博点赞
     * 因为DaoUtil不支持事务，改DaoUtil又太麻烦，所以这里采用手动回滚
     * <p>
     * 2019/3/3 16:56 好像不用事务更麻烦，但是我都已经写完了
     *
     * @param weiboId 要点赞的微博id
     * @param user    sessionUser
     */
    @Override
    public void like(int weiboId, User user) {
        String sql = "select user_id from weibo_praise where weibo_id=? and user_id=?";
        Long praiseNum = (Long) DaoUtil.getObject(sql, weiboId, user.getUserId());
        if (praiseNum != null) {
            throw new RuntimeException("已经点过赞了");
        }

        try {
            sql = "insert into weibo_praise(user_id,weibo_id,praise_time) values(?,?,now())";
            DaoUtil.query(sql, user.getUserId(), weiboId);
            sql = "update weibo_data set praise_num=praise_num+1 where weibo_id=?";
            DaoUtil.query(sql, weiboId);
        } catch (Exception e) {
            sql = "delete from weibo_praise where weibo_id=? and user_id=?";
            DaoUtil.query(sql, weiboId, user.getUserId());
            sql = "select count(*) from weibo_praise where weibo_id=?";
            Long num = (Long) DaoUtil.getObject(sql, weiboId);
            if (num != null) {
                int praise = num.intValue();
                sql = "update weibo_data set praise_num=? where weibo_id=?";
                DaoUtil.query(sql, praise, weiboId);
            }
            if (num == null) {
                sql = "update weibo_data set praise_num=0 where weibo_id=?";
                DaoUtil.query(sql, weiboId);
            }
            throw new RuntimeException("点赞出错");
        }
    }

    /**
     * 评论点赞
     *
     * @param comment 要点赞的评论
     */
    @Override
    public void like(Comment comment, User user) {
        String sql = "select user_id from comment_praise where user_id=? and comment_id=?";
        Comment comment1 = DaoUtil.toBeanSingle(Comment.class, sql, user.getUserId(), comment.getCommentId());
        if (comment1 != null) {
            throw new RuntimeException("已经点过赞了");
        }

        try {
            sql = "insert into comment_praise(user_id,comment_id) values(?,?)";
            DaoUtil.query(sql, user.getUserId(), comment.getCommentId());
            sql = "update weibo_comment set comment_praise=comment_praise+1 where comment_id=?";
            DaoUtil.query(sql, comment.getCommentId());
        } catch (Exception e) {
            sql = "delete from comment_praise where comment_id=? and user_id=?";
            DaoUtil.query(sql, comment.getCommentId(), user.getUserId());
            sql = "select count(*) from comment_praise where comment_id=?";
            Long num = (Long) DaoUtil.getObject(sql, comment.getCommentId());
            if (num != null) {
                int praise = num.intValue();
                sql = "update weibo_comment set comment_praise=? where comment_id=?";
                DaoUtil.query(sql, praise, comment.getCommentId());
            }
            if (num == null) {
                sql = "update weibo_comment set comment_praise=0 where comment_id=?";
                DaoUtil.query(sql, comment.getCommentId());
            }
            throw new RuntimeException("点赞出错");
        }
    }

    /**
     * 回复点赞
     *
     * @param replyComment 要点赞的回复
     */
    @Override
    public void like(ReplyComment replyComment, User user) {
        String sql = "select user_id from reply_praise where user_id=? and reply_id=?";
        ReplyComment replyComment1 = DaoUtil.toBeanSingle(ReplyComment.class, sql, user.getUserId(), replyComment.getReplyId());
        if (replyComment1 != null) {
            throw new RuntimeException("已经点过赞了");
        }

        try {
            sql = "insert into reply_praise(user_id,reply_id) values(?,?)";
            DaoUtil.query(sql, user.getUserId(), replyComment.getReplyId());
            sql = "update reply_comment set reply_praise=reply_praise+1 where reply_id=?";
            DaoUtil.query(sql, replyComment.getReplyId());
        } catch (Exception e) {
            sql = "delete from reply_praise where reply_id=? and user_id=?";
            DaoUtil.query(sql, replyComment.getReplyId(), user.getUserId());
            sql = "select count(*) from reply_praise where reply_id=?";
            Long num = (Long) DaoUtil.getObject(sql, replyComment.getReplyId());
            if (num != null) {
                int praise = num.intValue();
                sql = "update reply_comment set reply_praise=? where reply_id=?";
                DaoUtil.query(sql, praise, replyComment.getReplyId());
            }
            if (num == null) {
                sql = "update reply_comment set reply_praise=0 where reply_id=?";
                DaoUtil.query(sql, replyComment.getReplyId());
            }
            throw new RuntimeException("点赞出错");
        }
    }

    /**
     * 添加微博的评论，需要weiboId,commentId,userId
     *
     * @param weibo   .
     * @param comment .
     * @param user    .
     */
    @Override
    public void addComment(Weibo weibo, Comment comment, User user) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = df.format(new Date());
        String sql = "insert into weibo_comment(comment_content,comment_time,user_id,weibo_id) values(?,?,?,?)";
        DaoUtil.query(sql, comment.getCommentContent(), format, user.getUserId(), weibo.getWeiboId());
        /*
         * 获取刚刚插入的记录
         * 此处逻辑：
         *   因为同一用户在相同时间,插入同一微博多条评论的概率太低了，所以我们就认为这三条数据可以唯一表示一条评论
         * 虽然可能还是会出错，但是我想不到别的办法了 -2019/3/3 21:43
         * */
        sql = "select comment_id from weibo_comment where comment_time=? and user_id=? and weibo_id=?";
        Comment comment1 = DaoUtil.toBeanSingle(Comment.class, sql, format, user.getUserId(), weibo.getWeiboId());
        // TODO: 2019/3/3 此处获取评论id是有用的，用处暂时想不起来了
    }

    /**
     * 给评论添加回复
     *
     * @param comment .
     * @param user    sessionUser
     */
    @Override
    public void addReply(Comment comment, ReplyComment replyComment, User user) {
        String sql = "insert into reply_comment(comment_id,reply_content,reply_time,user_id) values(?,?,now(),?)";
        DaoUtil.query(sql, comment.getCommentId(), comment.getCommentContent(), user.getUserId());
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
        try {
            String sql = "insert into weibo_repost(user_id,weibo_id,repost_time) values(?,?,?)";
            DaoUtil.query(sql, user.getUserId(), weibo.getWeiboId(), format);
            sql = "select weibo_content,photo from weibo_data where weibo_id=?";
            Weibo weibo1 = DaoUtil.toBeanSingle(Weibo.class, sql, weibo.getWeiboId());
            if (weibo1 == null) {
                throw new RuntimeException("找不到要转发的微博");
            }
            sql = "select photo from weibo_data where weibo_id=?";
            String photo = (String) DaoUtil.getObject(sql, weibo.getWeiboId());
            sql = "insert into weibo_data(weibo_content,photo,is_origin,user_id,create_time) values(?,?,false,?,?)";
            DaoUtil.query(sql, weibo1.getWeiboContent(), photo, user.getUserId(), format);
            sql = "update user_info set weibo_num = weibo_num +1 where user_id = ?";
            DaoUtil.query(sql, user.getUserId());
        } catch (RuntimeException e) {
            String sql = "delete from weibo_repost where weibo_id=? and user_id=? and repost_time=?";
            DaoUtil.query(sql, weibo.getWeiboId(), user.getUserId(), format);
            //这句可能出问题，但是概率很低
            sql = "delete from weibo_data where user_id=? and create_time=?";
            DaoUtil.query(sql, user.getUserId(), format, weibo.getWeiboContent());
            throw new RuntimeException("转发失败");
        }
    }

    @Override
    public List<Weibo> getRepost(User user) {
        String sql = "select * from weibo_data where weibo_id in (select weibo_id from weibo_repost where user_id=? order by repost_time desc)";
        return DaoUtil.toBean(Weibo.class, sql, user.getUserId());
    }

    /**
     * 显示我发出的评论，这里把评论的回复也封装成了Comment对象，后期有时间的话会砍掉ReplyComment类，然后重新设计评论和回复部分的数据库
     *
     * @param user .
     * @return .
     */
    @Override
    public List<Comment> showCommentSend(User user) {
        String sql = "select * from weibo_comment where user_id=?";
        List<Comment> comments = DaoUtil.toBean(Comment.class, sql, user.getUserId());
        sql = "select * from reply_comment where user_id=?";
        List<ReplyComment> replyComments = DaoUtil.toBean(ReplyComment.class, sql, user.getUserId());
        toComment(comments, replyComments);
        return comments;
    }

    /**
     * 显示我收到的评论，这里把评论的回复也封装成了Comment对象，后期有时间的话会砍掉ReplyComment类，然后重新设计评论和回复部分的数据库
     *
     * @param user .
     * @return .
     */
    @Override
    public List<Comment> showCommentReceive(User user) {
        String sql = "select * from weibo_comment where weibo_id in (select weibo_id from weibo_data where user_id=?)";
        List<Comment> comments = DaoUtil.toBean(Comment.class, sql, user.getUserId());
        List<Comment> all = new ArrayList<>(comments);
        for (Comment comment : comments) {
            if (setProfilePicture(comment)) {
                continue;
            }
            sql = "select * from reply_comment where comment_id=?";
            List<ReplyComment> replyComments = DaoUtil.toBean(ReplyComment.class, sql, comment.getCommentId());
            toComment(all, replyComments);
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

    private void toComment(List<Comment> all, List<ReplyComment> replyComments) {
        String sql;
        for (ReplyComment r : replyComments) {
            sql = "select weibo_id from weibo_comment where comment_id=?";
            Long weiboIdL = (Long) DaoUtil.getObject(sql, r.getCommentId());
            if (weiboIdL != null) {
                int weiboId = weiboIdL.intValue();
                Comment c = new Comment();
                if (setProfilePicture(c)) {
                    continue;
                }
                c.setCommentId(-1);
                c.setWeiboId(weiboId);
                c.setCommentContent(r.getReplyContent());
                c.setCommentPraise(r.getReplyPraise());
                c.setCommentTime(r.getReplyTime());
                c.setNickname(r.getNickname());
                c.setUserId(r.getUserId());
                all.add(c);
            }
        }
    }
}
