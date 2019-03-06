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
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFormatReplyTime() {
        if (this.replyTime != null) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
