package com.example.qy.whs;


import android.app.Application;
import android.util.Log;


import com.example.qy.bean.UserInfo;
import com.example.qy.xiaoshipin.common.utils.TCConstants;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.ugc.TXUGCBase;



public class MyApplication extends Application {

    private static MyApplication instance;
    private String ugcKey = "dc050db7971b45da50ae2fc598ab37f7";
    private String ugcLicenceUrl = "http://license.vod2.myqcloud.com/license/v1/526101fca4f0ca8778f9420e9eef9266/TXUgcSDK.licence";

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


        instance = this;
        // 短视频licence设置
        TXUGCBase.getInstance().setLicence(instance, ugcLicenceUrl, ugcKey);
    }

    public static MyApplication getApplication() {
        return instance;
    }






}
