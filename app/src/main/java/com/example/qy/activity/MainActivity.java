package com.example.qy.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.qy.R;
import com.example.qy.adapter.MyPagerAdapter;
import com.example.qy.fragment.MainFragment;
import com.example.qy.fragment.TravelFragment;
import com.example.qy.ui.NoScrollHorizontalViewPager;
import com.example.qy.whs.BaseActivity;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

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
