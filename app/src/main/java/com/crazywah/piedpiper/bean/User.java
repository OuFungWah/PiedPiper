package com.crazywah.piedpiper.bean;

import android.text.TextUtils;

import java.util.Date;

public class User {

    public static String getGenderString(int gender) {
        switch (gender) {
            case 0:
                return "男";
            case 1:
                return "女";
            default:
                return "未知";
        }
    }

    /*---------以下属性来自用户表-----------*/

    /**
     * 成员ID
     */
    private long memberId;
    /**
     * 应用内ID
     */
    private String accountId;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 密码
     */
    private String password;
    /**
     * 头像Url
     */
    private String avatar;
    /**
     * 网易云IM token
     */
    private String token;
    /**
     * 性别 0:male 1:female 2:unknown
     */
    private int gender;
    /**
     * 地址
     */
    private String address;
    /**
     * 电话
     */
    private String mobile;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 注册时间
     */
    private Date registerTime;
    /**
     * 个性签名
     */
    private String signature;
    /**
     * 邮箱
     */
    private String email;

    /*---------以下属性来自好友关系表-----------*/

    /**
     * 关系标识 0-陌生人；1-好友；2-星标朋友；3-特别关注；4-黑名单；
     */
    private int relation;
    /**
     * 备注名
     */
    private String alias;
    /**
     * 附注信息
     */
    private String remark;
    /**
     * 添加为好友的时间
     */
    private Date friendTime;

    /*---------以下属性来自好友请求表------------*/

    /**
     * 请求信息
     */
    private String requestMessage;
    /**
     * 请求好友时间
     */
    private Date requestTime;
    /**
     * 请求状态
     */
    private int requestStatus;

    public int getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(int requestStatus) {
        this.requestStatus = requestStatus;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public int getGender() {
        return gender;
    }

    public String getGenderString() {
        return getGenderString(gender);
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public void setRelation(Integer relation) {
        this.relation = relation;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }

    public Date getFriendTime() {
        return friendTime;
    }

    public void setFriendTime(Date friendTime) {
        this.friendTime = friendTime;
    }

    public String getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(String requestMessage) {
        this.requestMessage = requestMessage;
    }

    public String getName() {
        return TextUtils.isEmpty(getAlias()) ? ((TextUtils.isEmpty(getNickname()) ? getAccountId() : getNickname())) : getAlias();
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
