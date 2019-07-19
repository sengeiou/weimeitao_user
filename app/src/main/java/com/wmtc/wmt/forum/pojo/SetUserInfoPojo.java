package com.wmtc.wmt.forum.pojo;

public class SetUserInfoPojo {
    private String userId;
    private String birthday;
    private String jiguangId;
    private String nickName;
    private String sex;
    private String userFuzhiName;
    private String userFuzhiId;

    public String getUserFuzhiId() {
        return userFuzhiId;
    }

    public void setUserFuzhiId(String userFuzhiId) {
        this.userFuzhiId = userFuzhiId;
    }

    private String loginCityName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJiguangId() {
        return jiguangId;
    }

    public void setJiguangId(String jiguangId) {
        this.jiguangId = jiguangId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserFuzhiName() {
        return userFuzhiName;
    }

    public void setUserFuzhiName(String userFuzhiName) {
        this.userFuzhiName = userFuzhiName;
    }

    public String getLoginCityName() {
        return loginCityName;
    }

    public void setLoginCityName(String loginCityName) {
        this.loginCityName = loginCityName;
    }
}
