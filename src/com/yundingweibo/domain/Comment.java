package com.yundingweibo.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author 杜奕明
 * @date 2019/3/3 10:41
 */
public class Comment {
    private int commentId;
    private String commentContent;
    private int commentPraise;
    private Date commentTime;
    private int userId;
    private String nickname;
    private int weiboId;
    private List<ReplyComment> replyComments;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public List<ReplyComment> getReplyComments() {
        return replyComments;
    }

    public void setReplyComments(List<ReplyComment> replyComments) {
        this.replyComments = replyComments;
    }

    public String getFormatCommentTime() {
        if (this.commentTime != null) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return sd.format(commentTime);
        } else {
            return null;
        }
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public int getCommentPraise() {
        return commentPraise;
    }

    public void setCommentPraise(int commentPraise) {
        this.commentPraise = commentPraise;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = (Date) commentTime.clone();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }
}
