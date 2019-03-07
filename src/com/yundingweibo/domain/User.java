package com.yundingweibo.domain;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用于显示个人主页
 *
 * @author 杜奕明
 * @date 2019/2/15 10:57
 */
public class User {
    private int userId;
    /**
     * 手机号
     */
    private String loginId;
    private String password;
    private String nickname;
    private String realName;
    private int gender;
    private String location;
    private int sexualOrientation;
    private int emotionalState;
    private Date birthday;
    private String signature;
    private Date registrationTime;
    private String email;
    private String qq;
    private String undergraduateSchool;
    private String graduateSchool;
    private List<String> tag;
    private int subscribeNum;
    private int fansNum;
    private int weiboNum;
    private String profilePicture;
    private String attentionGroup;
    private String verifyCode;

    public User() {
    }

    public User(int userId) {
        this.userId = userId;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public String getAttentionGroup() {
        if (attentionGroup == null) {
            return "未分组";
        }
        return attentionGroup;
    }

    public void setAttentionGroup(String attentionGroup) {
        this.attentionGroup = attentionGroup;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", nickname='" + nickname + '\'' +
                ", realName='" + realName + '\'' +
                '}';
    }

    public Date getBirthday() {
        return birthday;
    }

    public Date getRegistrationTime() {
        return registrationTime;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getNickname() {
        if (nickname == null) {
            return "用户" + userId;
        }
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getSexualOrientation() {
        return sexualOrientation;
    }

    public void setSexualOrientation(int sexualOrientation) {
        this.sexualOrientation = sexualOrientation;
    }

    public int getEmotionalState() {
        return emotionalState;
    }

    public void setEmotionalState(int emotionalState) {
        this.emotionalState = emotionalState;
    }

    public String getFormatBirthday() {
        if (this.birthday != null) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return sd.format(birthday);
        } else {
            return null;
        }
    }

    public void setBirthday(Date birthday) {
        this.birthday = (Date) birthday.clone();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getFormatRegistrationTime() {
        if (this.registrationTime != null) {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            return sd.format(registrationTime);
        } else {
            return null;
        }
    }

    public void setRegistrationTime(Date registrationTime) {
        this.registrationTime = (Date) registrationTime.clone();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getUndergraduateSchool() {
        return undergraduateSchool;
    }

    public void setUndergraduateSchool(String undergraduateSchool) {
        this.undergraduateSchool = undergraduateSchool;
    }

    public String getGraduateSchool() {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool) {
        this.graduateSchool = graduateSchool;
    }

    public int getSubscribeNum() {
        return subscribeNum;
    }

    public void setSubscribeNum(int subscribeNum) {
        this.subscribeNum = subscribeNum;
    }

    public int getFansNum() {
        return fansNum;
    }

    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }

    public int getWeiboNum() {
        return weiboNum;
    }

    public void setWeiboNum(int weiboNum) {
        this.weiboNum = weiboNum;
    }
}
