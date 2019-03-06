package com.example.qy.bean;

public class UserInfo {
    public String birthday;
    public String phone;
    public String signature;
    public String sex;
    public String nickname;
    public String icon;
    public String integral;
    public String manifesto;
    public String home;
    public String fans;
    public String follow;
    public String qianyiID;
    public int loginId;

    @Override
    public String toString() {
        return "UserInfo{" +
                "birthday='" + birthday + '\'' +
                ", phone='" + phone + '\'' +
                ", signature='" + signature + '\'' +
                ", sex='" + sex + '\'' +
                ", nickname='" + nickname + '\'' +
                ", icon='" + icon + '\'' +
                ", integral='" + integral + '\'' +
                ", manifesto='" + manifesto + '\'' +
                ", home='" + home + '\'' +
                ", fans='" + fans + '\'' +
                ", qianyiID='" + qianyiID + '\'' +
                ", loginId=" + loginId +
                '}';
    }
}
