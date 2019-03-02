package com.example.qy.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.qy.R;
import com.example.qy.adapter.MyPagerAdapter;
import com.example.qy.bean.UserInfo;
import com.example.qy.fragment.MainFragment;
import com.example.qy.fragment.TravelFragment;
import com.example.qy.ui.NoScrollHorizontalViewPager;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.UniquePsuedoUtils;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;
import com.jaeger.library.StatusBarUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {
//    private HomeFragment mHomeFragment;
//    private MyFragment mMyFragment;
//    private LinearLayout mLiHome,mLiFocus,mLiCamera,mLiShopping,mLiMy;
    private NoScrollHorizontalViewPager vp_main;
    private MainFragment mainFragment;
    private TravelFragment travelFragment;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        initView();
    }


    private void initView(){
//        initFragment("home");
//        mLiHome = findViewById(R.id.li_home);
//        mLiFocus = findViewById(R.id.li_focus);
//        mLiCamera = findViewById(R.id.li_camera);
//        mLiShopping = findViewById(R.id.li_shopping);
//        mLiMy = findViewById(R.id.li_my);
//
//        mLiHome.setOnClickListener(this);
//        mLiMy.setOnClickListener(this);


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

        vp_main = findViewById(R.id.vp_main);
        fragmentList = new ArrayList<>();
        mainFragment = new MainFragment();
        travelFragment = new TravelFragment();
        fragmentList.add(travelFragment);
        fragmentList.add(mainFragment);
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList);

        vp_main.setAdapter(adapter);
        vp_main.setCurrentItem(1);
    }

//    private void initFragment(String tag){
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction  = fragmentManager.beginTransaction();
//        // 隐藏所有的fragment
//        hideFragment(transaction);
//        switch (tag){
//            case "home":
//                if (mHomeFragment == null){
//                    mHomeFragment = new HomeFragment();
//                    transaction.add(R.id.fm_main,mHomeFragment);
//                }
//                transaction.show(mHomeFragment);
//                break;
//            case "my":
//                if (mMyFragment == null){
//                    mMyFragment = new MyFragment();
//                    transaction.add(R.id.fm_main,mMyFragment);
//                }
//                transaction.show(mMyFragment);
//                break;
//        }
//        transaction.commit();
//    }

    //隐藏所有的fragment
//    private void hideFragment(FragmentTransaction transaction) {
//        if (mHomeFragment != null) {
//            transaction.hide(mHomeFragment);
//        }
//        if (mMyFragment != null) {
//            transaction.hide(mMyFragment);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.li_home:
//                initFragment("home");
//                break;
//            case R.id.li_focus:
//                break;
//            case R.id.li_camera:
//                break;
//            case R.id.li_shopping:
//                break;
//            case R.id.li_my:
//                initFragment("my");
//                break;
        }
    }

}
