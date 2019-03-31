package com.yundingweibo.service;

import com.yundingweibo.dao.DaoFactory;
import com.yundingweibo.dao.UserDao;
import com.yundingweibo.dao.WeiboDao;
import com.yundingweibo.dao.impl.daoutil.DaoUtil;
import com.yundingweibo.domain.Comment;
import com.yundingweibo.domain.PageBean;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;

import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author 杜奕明
 * @date 2019/2/18 17:25
 */
public class WeiboService {
    UserDao userDao = DaoFactory.getUserDao();

    public PageBean<Weibo> getWeiboByUserId(int userId, int pageCode, int pageSize) {
        WeiboDao weiboDao = DaoFactory.getWeiboDao();
        return weiboDao.getWeiboByUserId(userId, pageCode, pageSize);
    }

    /**
     * 分页显示微博，查找的是user关注的用户的微博，微博按创建时间排序
     *
     * @param user     .
     * @param pageCode .
     * @param pageSize .
     * @return .
     */
    public PageBean<Weibo> findAll(User user, int pageCode, int pageSize) {
        WeiboDao weiboDao = DaoFactory.getWeiboDao();

        return weiboDao.findAll(user, pageCode, pageSize);
    }

    public PageBean<Weibo> findPraise(User user, int pageCode, int pageSize) {
        return DaoFactory.getWeiboDao().findPraise(user, pageCode, pageSize);
    }

    /**
     * 用于排行榜
     *
     * @param type praise为点赞榜，repost为转发榜
     * @return type的类型传的啥就返回的是什么排行榜
     * @throws RuntimeException 传入参数不是praise或者repost时抛出
     */
    public List<Weibo> showList(String type) {
        List<Weibo> weiboList = null;
        switch (type) {
            case "praise":
                weiboList = DaoFactory.getWeiboDao().getPraiseList();
                break;
            case "repost":
                weiboList = DaoFactory.getWeiboDao().getRepostList();
                break;
            default:
                //这步基本不可能被执行
                throw new RuntimeException("形参type未知");
        }
        //weiboList应该不可能为空

        int min;
        int max;

        for (Weibo w : weiboList) {
            String content = w.getWeiboContent();
            if (content.length() > 25) {
                max = 25;
                min = 22;

            } else {
                max = content.length() - 1;
                min = max / 3;
            }
            int index = new Random().nextInt(max - min) + min;
            content = content.substring(0, index);
            content += "...";
            w.setWeiboContent(content);
        }
        return weiboList;
    }

    /**
     * 点赞微博
     * 只需要weiboId和userId
     *
     * @param weiboId .
     * @param user    .
     */
    public int praiseWeibo(int weiboId, User user) {
        return DaoFactory.getWeiboDao().like(weiboId, user);
    }

    /**
     * 点赞评论
     * 只需要weiboId和userId
     *
     * @param comment .
     * @param user    .
     */
    public int praiseComment(Comment comment, User user) {
        return DaoFactory.getWeiboDao().like(comment, user);
    }

    /**
     * 添加评论，需要comment_content,user_id,weibo_id
     *
     * @param weibo   .
     * @param comment .
     * @param user    .
     */
    public Comment addComment(Weibo weibo, Comment comment, User user) {
        Date commentTime = new Date();
        comment.setCommentTime(commentTime);
        WeiboDao weiboDao = DaoFactory.getWeiboDao();
        weiboDao.addComment(weibo, comment, user);

        return weiboDao.findComment(comment);
    }

    /**
     * 添加回复，需要comment_id,reply_content,user_id
     *
     * @param comment      .
     * @param replyComment .
     * @param user         .
     */
    public Comment addReply(Comment comment, Comment replyComment, User user) {
        Date commentTime = new Date();
        replyComment.setCommentTime(commentTime);

        WeiboDao weiboDao = DaoFactory.getWeiboDao();

        replyComment.setFloor(0);
        Comment parentComment = weiboDao.findCommentByCommentId(comment);
        String userNickname = userDao.getUserNickname(parentComment.getUserId());
        weiboDao.addReply(comment, replyComment, user);

        Comment comment1 = weiboDao.findComment(replyComment);
        comment1.setParentNickname(userNickname);

        Long parentFloor = (Long) DaoUtil.getObject("select floor from weibo_comment where comment_id=?", comment.getCommentId());
        if (parentFloor != null) {
            comment1.setParentFloor(parentFloor.intValue());
        }
        return comment1;
    }

    /**
     * 只需要userId
     *
     * @param user .
     * @param type 1是send 2是receive
     * @return .
     */
    public List<Comment> showCommentAndReply(User user, int type) {
        WeiboDao weiboDao = DaoFactory.getWeiboDao();
        UserDao userDao = DaoFactory.getUserDao();
        List<Comment> comments;
        switch (type) {
            case 1:
                comments = weiboDao.showCommentSend(user);
                break;
            case 2:
                comments = weiboDao.showCommentReceive(user);
                break;
            default:
                throw new RuntimeException("type类型未知");
        }

        for (Comment c : comments) {
            Weibo weibo = weiboDao.getWeibo(c.getWeiboId());
            c.setNickname(userDao.getUserNickname(weibo.getUserId()));
            String content = weibo.getWeiboContent();
            if (content.length() > 42) {
                content = content.substring(0, 39);
                content += "...";
            }
            c.setWeiboContent(content);
        }
        return comments;
    }

    public int addWeibo(User user, Weibo weibo) {
        return DaoFactory.getWeiboDao().addWeibo(user, weibo);
    }

    public PageBean<Weibo> showAll(int pageCode, int pageSize) {
        return DaoFactory.getWeiboDao().showAll(pageCode, pageSize);
    }
}
