package com.yundingweibo.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 杜奕明
 * @date 2019/3/3 10:50
 */
public class ReplyComment {
    private int replyId;
    private int commentId;
    private String replyContent;
    private Date replyTime;
    private int replyPraise;
    private int userId;
    private String nickname;
    private int weiboId;

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getReplyPraise() {
        return replyPraise;
    }

    public void setReplyPraise(int replyPraise) {
        this.replyPraise = replyPraise;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFormatReplyTime() {
        if (this.replyTime != null) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sd.format(replyTime);
        } else {
            return null;
        }
    }

    public int getReplyId() {
        return replyId;
    }

    public void setReplyId(int replyId) {
        this.replyId = replyId;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public Date getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(Date replyTime) {
        this.replyTime = (Date) replyTime.clone();
    }
}
