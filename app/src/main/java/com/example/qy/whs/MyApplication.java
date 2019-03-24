package com.example.qy.whs;

import android.app.Application;

import com.example.qy.bean.UserInfo;


public class MyApplication extends Application {
    /*
    把手机号定义为全局变量
     */
//    private String phone;
//    public void setPhone(String phone){
//        this.phone = phone;
//    }
//    public String getPhone(){
//        return phone;
//    }

    private UserInfo userInfo;

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 配置LitePal
//        LitePal.initialize(this);
    }
}
