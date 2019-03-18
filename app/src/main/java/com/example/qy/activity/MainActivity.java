package com.example.qy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.bean.UserInfo;
import com.example.qy.fragment.FocusFragment;
import com.example.qy.fragment.HomeFragment;
import com.example.qy.fragment.MallFragment;
import com.example.qy.fragment.MyFragment;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private HomeFragment mHomeFragment;
    private MyFragment mMyFragment;
    private FocusFragment mFocusFragment;
    private MallFragment mMallFragment;
    private LinearLayout mLiHome,mLiFocus,mLiCamera,mLiShopping,mLiMy;
    private LinearLayout li_bottom;
    private ImageView iv_home,iv_focus,iv_shopping,iv_my;
    private TextView tv_home,tv_focus,tv_shopping,tv_my;

    public DrawerLayout dl_mall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        initView();
    }


    private void initView(){

//        // 修改完后可能不会调取这个，但是问题不大，暂时先这样写
//        String phone = getIntent().getStringExtra("phone");
//        String phoneId = UniquePsuedoUtils.getUniquePsuedoID();
//        HttpUtils.sendOkHttpRequest(HttpQYUtils.getValidationLogin(phone, phoneId), new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {}
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseText = response.body().string();
//                try {
//                    JSONObject jsonObject = new JSONObject(responseText);
//                    boolean isSuc = jsonObject.getBoolean("isSuc");
//                    if (isSuc){
//                        UserInfo userInfo = new UserInfo();
//                        JSONObject dataObject = jsonObject.getJSONObject("data");
//                        userInfo.birthday = dataObject.getString("birthday");
//                        userInfo.phone = dataObject.getString("phone");
//                        userInfo.signature = dataObject.getString("signature");
//                        userInfo.integral = dataObject.getString("integral");
//                        userInfo.sex = dataObject.getString("sex");
//                        userInfo.nickname = dataObject.getString("nickname");
//                        userInfo.icon = dataObject.getString("icon");
//                        userInfo.manifesto = dataObject.getString("manifesto");
//                        userInfo.home = dataObject.getString("home");
//                        userInfo.fans = dataObject.getString("fans");
//                        userInfo.qianyiID = dataObject.getString("qianyiID");
//                        // 登录成功 保存登录状态
//                        MyApplication application = (MyApplication) getApplication();
//                        application.setUserInfo(userInfo);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        dl_mall = findViewById(R.id.dl_mall);
        //禁止手势滑动
        dl_mall.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        // 自动登录
        iv_home = findViewById(R.id.iv_home);
        iv_focus = findViewById(R.id.iv_focus);
        iv_shopping = findViewById(R.id.iv_shopping);
        iv_my = findViewById(R.id.iv_my);

        li_bottom = findViewById(R.id.li_bottom);

        tv_home = findViewById(R.id.tv_home);
        tv_focus = findViewById(R.id.tv_focus);
        tv_shopping = findViewById(R.id.tv_shopping);
        tv_my = findViewById(R.id.tv_my);



        initFragment("home");
        mLiHome = findViewById(R.id.li_home);
        mLiFocus = findViewById(R.id.li_focus);
        mLiCamera = findViewById(R.id.li_camera);
        mLiShopping = findViewById(R.id.li_shopping);
        mLiMy = findViewById(R.id.li_my);

        mLiHome.setOnClickListener(this);
        mLiMy.setOnClickListener(this);
        mLiFocus.setOnClickListener(this);
        mLiShopping.setOnClickListener(this);

    }
    private void showHome(){

        iv_home.setImageResource(R.mipmap.home);
        iv_focus.setImageResource(R.mipmap.guazhu);
        iv_shopping.setImageResource(R.mipmap.gouwu);
        iv_my.setImageResource(R.mipmap._wo);

        tv_home.setTextColor(Color.parseColor("#fefefe"));
        tv_focus.setTextColor(Color.parseColor("#7ffefefe"));
        tv_shopping.setTextColor(Color.parseColor("#7ffefefe"));
        tv_my.setTextColor(Color.parseColor("#7ffefefe"));

        li_bottom.setBackgroundResource(0);
    }
    private void showOther(String type){

        switch (type){
            case "focus":
                iv_focus.setImageResource(R.mipmap.guazhu02);
                iv_shopping.setImageResource(R.mipmap.gouwu01);
                iv_my.setImageResource(R.mipmap._wo01);
                tv_focus.setTextColor(Color.parseColor("#1a1a1a"));
                tv_shopping.setTextColor(Color.parseColor("#B3B3B3"));
                tv_my.setTextColor(Color.parseColor("#B3B3B3"));
                break;
            case "shopping":
                iv_shopping.setImageResource(R.mipmap.gouwu02);
                iv_focus.setImageResource(R.mipmap.guazhu01);
                iv_my.setImageResource(R.mipmap._wo01);
                tv_shopping.setTextColor(Color.parseColor("#1a1a1a"));
                tv_focus.setTextColor(Color.parseColor("#B3B3B3"));
                tv_my.setTextColor(Color.parseColor("#B3B3B3"));
                break;
            case "my":
                iv_my.setImageResource(R.mipmap._wo02);
                iv_focus.setImageResource(R.mipmap.guazhu01);
                iv_shopping.setImageResource(R.mipmap.gouwu01);
                tv_my.setTextColor(Color.parseColor("#1a1a1a"));
                tv_focus.setTextColor(Color.parseColor("#B3B3B3"));
                tv_shopping.setTextColor(Color.parseColor("#B3B3B3"));
                break;

        }
        li_bottom.setBackgroundColor(Color.parseColor("#ffffff"));
        tv_home.setTextColor(Color.parseColor("#B3B3B3"));
        iv_home.setImageResource(R.mipmap.home01);
    }

    private void initFragment(String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction  = fragmentManager.beginTransaction();
        // 隐藏所有的fragment
        hideFragment(transaction);
        switch (tag){
            case "home":
                if (mHomeFragment == null){
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fm_main,mHomeFragment);
                }
                transaction.show(mHomeFragment);

                break;
            case "my":
                if (mMyFragment == null){
                    mMyFragment = new MyFragment();
                    transaction.add(R.id.fm_main,mMyFragment);
                }
                transaction.show(mMyFragment);
                break;
            case "focus":
                if (mFocusFragment == null){
                    mFocusFragment = new FocusFragment();
                    transaction.add(R.id.fm_main, mFocusFragment);
                }
                transaction.show(mFocusFragment);
                break;
            case "shopping":
                if (mMallFragment == null){
                    mMallFragment = new MallFragment();
                    transaction.add(R.id.fm_main, mMallFragment);
                }
                transaction.show(mMallFragment);
                break;
        }
        transaction.commit();
    }
    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
        if (mFocusFragment != null){
            transaction.hide(mFocusFragment);
        }
        if (mMallFragment != null){
            transaction.hide(mMallFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_home:
                initFragment("home");
                showHome();
                break;
            case R.id.li_focus:
                initFragment("focus");
                showOther("focus");
                break;
            case R.id.li_camera:
                break;
            case R.id.li_shopping:
                initFragment("shopping");
                showOther("shopping");
                break;
            case R.id.li_my:
                MyApplication application = (MyApplication) getApplication();
                if (application.getUserInfo() != null){
                    initFragment("my");
                    showOther("my");
                }else{
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
                break;
        }
    }

}
