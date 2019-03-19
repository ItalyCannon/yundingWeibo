package com.yundingweibo.dao;

import com.yundingweibo.domain.*;

import java.util.List;

/**
 * 显示文章
 *
 * @author 杜奕明
 * @date 2019/2/15 11:21
 */
public interface WeiboDao {
    /**
     * 根据微博id查询微博
     *
     * @param weiboId 微博id
     * @return 封装好的Weibo对象
     */
    Weibo getWeibo(int weiboId);

    /**
     * 根据用户id查询微博
     *
     * @param userId   用户id
     * @param pageCode 页码
     * @param pageSize 一页显示几条记录
     * @return 用户id对应的所有微博
     */
    PageBean<Weibo> getWeiboByUserId(int userId, int pageCode, int pageSize);

    /**
     * 分页显示微博，查找的是user关注的用户的微博，微博按创建时间排序
     *
     * @param user     .
     * @param pageCode 页码
     * @param pageSize 一页显示几条记录
     * @return .
     */
    PageBean<Weibo> findAll(User user, int pageCode, int pageSize);

    /**
     * 分页显示我的点赞
     *
     * @param user     .
     * @param pageCode .
     * @param pageSize .
     * @return .
     */
    PageBean<Weibo> findPraise(User user, int pageCode, int pageSize);

    /**
     * 点赞排行榜前25名
     *
     * @return 点赞排行榜前25名的Weibo对象
     */
    List<Weibo> getPraiseList();

    /**
     * 转发排行榜前25名
     *
     * @return 转发排行榜前25名的Weibo对象
     */
    List<Weibo> getRepostList();

    /**
     * 根据发过来的weibo的weiboId查找本微博的所有直接评论，包括评论的回复
     *
     * @param weibo .
     * @return .
     */
    List<Comment> getComment(Weibo weibo);

    /**
     * 微博点赞
     *
     * @param weiboId 要点赞的微博Id
     * @param user    sessionUser
     */
    void like(int weiboId, User user);

    /**
     * 评论点赞
     *
     * @param comment 要点赞的评论
     * @param user    sessionUser
     */
    void like(Comment comment, User user);

    /**
     * 回复点赞
     *
     * @param replyComment 要点赞的回复
     * @param user         sessionUser
     */
    void like(ReplyComment replyComment, User user);

    /**
     * 添加微博的评论，需要weiboId,commentId,userId
     *
     * @param weibo   .
     * @param comment .
     * @param user    .
     */
    void addComment(Weibo weibo, Comment comment, User user);

    /**
     * 给评论添加回复
     *
     * @param comment .
     * @param user    sessionUser
     */
    void addReply(Comment comment, ReplyComment replyComment, User user);

    /**
     * 删除转发
     *
     * @param user  .
     * @param weibo .
     */
    void deleteRepost(User user, Weibo weibo);

    /**
     * 转发
     *
     * @param user  .
     * @param weibo .
     */
    void addRepost(User user, Weibo weibo);

    List<Weibo> getRepost(User user);

    /**
     * 显示我发出的评论，这里把评论的回复也封装成了Comment对象，后期有时间的话会砍掉ReplyComment类，然后重新设计评论和回复部分的数据库
     *
     * @param user .
     * @return .
     */
    List<Comment> showCommentSend(User user);

    /**
     * 显示我收到的评论，这里把评论的回复也封装成了Comment对象，后期有时间的话会砍掉ReplyComment类，然后重新设计评论和回复部分的数据库
     *
     * @param user .
     * @return .
     */
    List<Comment> showCommentReceive(User user);

    /**
     * .
     *
     * @param user  .
     * @param weibo .
     */
    void addWeibo(User user, Weibo weibo);

    /**
     * 用于随机展示微博
     *
     * @param pageCode .
     * @param pageSize .
     * @return .
     */
    PageBean<Weibo> showAll(int pageCode, int pageSize);
}
