package test;

import java.util.Date;
import java.util.List;

/**
 * @author 杜奕明
 * @date 2019/3/26 15:38
 */
public class TestComment {
    private int commentId;
    private String commentContent;
    private int commentPraise;
    private Date commentTime;
    private int userId;
    private String nickname;
    private int weiboId;
    private List<TestComment> reply;
    private String weiboContent;
    private String profilePicture;
    private int parent;
    private List<TestComment> children;

    @Override
    public String toString() {
        return "TestComment{" +
                "commentId=" + commentId +
                ", commentContent='" + commentContent + '\'' +
                ", commentPraise=" + commentPraise +
                ", commentTime=" + commentTime +
                ", userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", weiboId=" + weiboId +
                ", reply=" + reply +
                ", weiboContent='" + weiboContent + '\'' +
                ", profilePicture='" + profilePicture + '\'' +
                ", parent=" + parent +
                '}';
    }

    public List<TestComment> getChildren() {
        return children;
    }

    public void setChildren(List<TestComment> children) {
        this.children = children;
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
        this.commentTime = commentTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(int weiboId) {
        this.weiboId = weiboId;
    }

    public List<TestComment> getReply() {
        return reply;
    }

    public void setReply(List<TestComment> reply) {
        this.reply = reply;
    }

    public String getWeiboContent() {
        return weiboContent;
    }

    public void setWeiboContent(String weiboContent) {
        this.weiboContent = weiboContent;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }
}
