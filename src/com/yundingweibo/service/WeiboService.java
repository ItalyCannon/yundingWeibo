package com.yundingweibo.service;

import com.yundingweibo.dao.DaoFactory;
import com.yundingweibo.dao.UserDao;
import com.yundingweibo.dao.WeiboDao;
import com.yundingweibo.domain.*;

import java.util.List;
import java.util.Random;

/**
 * @author 杜奕明
 * @date 2019/2/18 17:25
 */
public class WeiboService {
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
        return DaoFactory.getWeiboDao().findAll(user, pageCode, pageSize);
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
    public void praiseWeibo(int weiboId, User user) {
        DaoFactory.getWeiboDao().like(weiboId, user);
    }

    /**
     * 点赞评论
     * 只需要weiboId和userId
     *
     * @param comment .
     * @param user    .
     */
    public void praiseComment(Comment comment, User user) {
        DaoFactory.getWeiboDao().like(comment, user);
    }

    /**
     * 点赞回复
     * 只需要weiboId和userId
     *
     * @param replyComment .
     * @param user         .
     */
    public void praiseReply(ReplyComment replyComment, User user) {
        DaoFactory.getWeiboDao().like(replyComment, user);
    }

    /**
     * 添加评论，需要comment_content,user_id,weibo_id
     *
     * @param weibo   .
     * @param comment .
     * @param user    .
     */
    public void addComment(Weibo weibo, Comment comment, User user) {
        DaoFactory.getWeiboDao().addComment(weibo, comment, user);
    }

    /**
     * 添加回复，需要comment_id,reply_content,user_id
     *
     * @param comment      .
     * @param replyComment .
     * @param user         .
     */
    public void addReply(Comment comment, ReplyComment replyComment, User user) {
        DaoFactory.getWeiboDao().addReply(comment, replyComment, user);
    }

    /**
     * 只需要userId，这里把评论回复也处理成评论了，不过评论回复的commentId是-1
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
}
