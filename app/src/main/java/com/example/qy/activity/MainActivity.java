package com.example.qy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.LinearLayout;
import com.example.qy.R;
import com.example.qy.bean.UserInfo;
import com.example.qy.fragment.FocusFragment;
import com.example.qy.fragment.HomeFragment;
import com.example.qy.fragment.MyFragment;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.UniquePsuedoUtils;
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
    private LinearLayout mLiHome,mLiFocus,mLiCamera,mLiShopping,mLiMy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView(){

        // 修改完后可能不会调取这个，暂时先这样写
        String phone = getIntent().getStringExtra("phone");
        String phoneId = UniquePsuedoUtils.getUniquePsuedoID();
        HttpUtils.sendOkHttpRequest(HttpQYUtils.getValidationLogin(phone, phoneId), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseText);
                    boolean isSuc = jsonObject.getBoolean("isSuc");
                    if (isSuc){
                        UserInfo userInfo = new UserInfo();
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        userInfo.birthday = dataObject.getString("birthday");
                        userInfo.phone = dataObject.getString("phone");
                        userInfo.signature = dataObject.getString("signature");
                        userInfo.integral = dataObject.getString("integral");
                        userInfo.sex = dataObject.getString("sex");
                        userInfo.nickname = dataObject.getString("nickname");
                        userInfo.icon = dataObject.getString("icon");
                        userInfo.manifesto = dataObject.getString("manifesto");
                        userInfo.home = dataObject.getString("home");
                        userInfo.fans = dataObject.getString("fans");
                        userInfo.qianyiID = dataObject.getString("qianyiID");
                        // 登录成功 保存登录状态
                        MyApplication application = (MyApplication) getApplication();
                        application.setUserInfo(userInfo);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });


        initFragment("home");
        mLiHome = findViewById(R.id.li_home);
        mLiFocus = findViewById(R.id.li_focus);
        mLiCamera = findViewById(R.id.li_camera);
        mLiShopping = findViewById(R.id.li_shopping);
        mLiMy = findViewById(R.id.li_my);

        mLiHome.setOnClickListener(this);
        mLiMy.setOnClickListener(this);
        mLiFocus.setOnClickListener(this);

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_home:
                initFragment("home");
                break;
            case R.id.li_focus:
                initFragment("focus");
                break;
            case R.id.li_camera:
                break;
            case R.id.li_shopping:
                break;
            case R.id.li_my:
                MyApplication application = (MyApplication) getApplication();
                if (application.getUserInfo() != null){
                    initFragment("my");
                }else{
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
                break;
        }
    }

}
