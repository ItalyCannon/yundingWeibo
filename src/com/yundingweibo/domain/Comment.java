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
    private String weiboContent;
    private String profilePicture;
    private int parent;
    private List<Comment> children;

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public List<Comment> getChildren() {
        return children;
    }

    public void setChildren(List<Comment> children) {
        this.children = children;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getWeiboContent() {
        return weiboContent;
    }

    public void setWeiboContent(String weiboContent) {
        this.weiboContent = weiboContent;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getFormatCommentTime() {
        if (this.commentTime != null) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
