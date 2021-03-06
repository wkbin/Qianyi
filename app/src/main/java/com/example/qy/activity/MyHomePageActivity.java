package com.example.qy.activity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.example.qy.R;
import com.example.qy.adapter.MyPagerAdapter;
import com.example.qy.bean.UserInfo;
import com.example.qy.fragment.CollectFragment;
import com.example.qy.fragment.LikeFragment;
import com.example.qy.fragment.ProductionFragment;
import com.example.qy.imp.AppBarStateChangeListener;
import com.example.qy.ui.LoadingDialog;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;
import com.jaeger.library.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

public class MyHomePageActivity extends BaseActivity implements View.OnClickListener {
    private de.hdodenhof.circleimageview.CircleImageView cv_my_icon;
    private TextView tv_my_nickname;
    private TextView tv_my_id;
    private TextView tv_my_signature;
    private TextView tv_name2;
    private TextView tv_update_data;
    private ImageView iv_back;
    private ImageView iv_back2;
    private ImageView iv_my_code;
    private ImageView iv_code_2;
    private ImageView iv_update;
    private ViewPager vp_home_page;
    private List<Fragment> fragmentList;
    private TextView tv_production,tv_collect,tv_like;
    private View v_production,v_collect,v_like;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout app_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTransparent(MyHomePageActivity.this);
        setContentView(R.layout.activity_my_home_page);

        initView();

        initData();
    }

    private void initView(){

        cv_my_icon = findViewById(R.id.cv_my_icon);
        tv_my_nickname = findViewById(R.id.tv_my_nickname);
        tv_my_id = findViewById(R.id.tv_my_id);
        tv_my_signature = findViewById(R.id.tv_my_signature);
        tv_update_data = findViewById(R.id.tv_update_data);
        iv_back = findViewById(R.id.iv_back);
        iv_back2 = findViewById(R.id.iv_back2);
        iv_my_code = findViewById(R.id.iv_my_code);
        iv_code_2 = findViewById(R.id.iv_code_2);
        vp_home_page = findViewById(R.id.vp_home_page);
        tv_name2 = findViewById(R.id.tv_name2);

        tv_production = findViewById(R.id.tv_production);
        tv_collect = findViewById(R.id.tv_collect);
        tv_like = findViewById(R.id.tv_like);
        iv_update = findViewById(R.id.iv_update);

        tv_production.setOnClickListener(this);
        tv_collect.setOnClickListener(this);
        tv_like.setOnClickListener(this);
        iv_update.setOnClickListener(this);


        v_production = findViewById(R.id.v_production);
        v_collect = findViewById(R.id.v_collect);
        v_like = findViewById(R.id.v_like);

        tv_update_data.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_back2.setOnClickListener(this);
        iv_my_code.setOnClickListener(this);
        iv_code_2.setOnClickListener(this);

        fragmentList = new ArrayList<>();
        fragmentList.add(new ProductionFragment());
        fragmentList.add(new CollectFragment());
        fragmentList.add(new LikeFragment());

        //toolbar
        toolbar = findViewById(R.id.toolbar);

        app_bar = findViewById(R.id.app_bar);

        //CollapsingToolbarLayout
        collapsingToolbarLayout = findViewById(R.id.collapsingToolbar);
        app_bar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {
                    //展开状态

                }else if(state == State.COLLAPSED){

                    //折叠状态
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    toolbar.setVisibility(View.VISIBLE);
                }else {

                    //中间状态
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });
//        collapsingToolbarLayout.setTitle("我是一个标题啊哈哈哈");

    }
    private void initData(){

        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            if (!TextUtils.isEmpty(userInfo.nickname)) {
                tv_my_nickname.setText(userInfo.nickname);
                tv_name2.setText(userInfo.nickname);
            }
            if (!TextUtils.isEmpty(userInfo.qianyiID)) {
                tv_my_id.setText("千艺号："+userInfo.qianyiID);
            }
            if (!TextUtils.isEmpty(userInfo.signature)) {
                tv_my_signature.setText(userInfo.signature);
            }
            if (!TextUtils.isEmpty(userInfo.icon)) {
                Glide.with(MyHomePageActivity.this).load(userInfo.icon).into(cv_my_icon);
            }
        }


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList);
        vp_home_page.setAdapter(adapter);
        vp_home_page.setCurrentItem(1);

        vp_home_page.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {}

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        tv_production.setTextSize(20);
                        tv_collect.setTextSize(16);
                        tv_like.setTextSize(16);

                        v_production.setVisibility(View.VISIBLE);
                        v_collect.setVisibility(View.GONE);
                        v_like.setVisibility(View.GONE);

                        tv_production.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        tv_collect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        tv_like.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                        tv_production.setTextColor(Color.parseColor("#1a1a1a"));
                        tv_collect.setTextColor(Color.parseColor("#b31a1a1a"));
                        tv_like.setTextColor(Color.parseColor("#b31a1a1a"));
                        break;
                    case 1:
                        tv_production.setTextSize(16);
                        tv_collect.setTextSize(20);
                        tv_like.setTextSize(16);

                        v_production.setVisibility(View.GONE);
                        v_collect.setVisibility(View.VISIBLE);
                        v_like.setVisibility(View.GONE);

                        tv_production.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        tv_collect.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                        tv_like.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));

                        tv_production.setTextColor(Color.parseColor("#b31a1a1a"));
                        tv_collect.setTextColor(Color.parseColor("#1a1a1a"));
                        tv_like.setTextColor(Color.parseColor("#b31a1a1a"));
                        break;
                    case 2:
                        tv_production.setTextSize(16);
                        tv_collect.setTextSize(16);
                        tv_like.setTextSize(20);

                        v_production.setVisibility(View.GONE);
                        v_collect.setVisibility(View.GONE);
                        v_like.setVisibility(View.VISIBLE);

                        tv_production.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        tv_collect.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
                        tv_like.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));

                        tv_production.setTextColor(Color.parseColor("#b31a1a1a"));
                        tv_collect.setTextColor(Color.parseColor("#b31a1a1a"));
                        tv_like.setTextColor(Color.parseColor("#1a1a1a"));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {}
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2){
            if (resultCode == RESULT_OK){
                // 信息修改后回调
                MyApplication application = (MyApplication) getApplication();
                UserInfo userInfo = application.getUserInfo();
                Glide.with(MyHomePageActivity.this).load(userInfo.icon).into(cv_my_icon);
                tv_my_nickname.setText(userInfo.nickname);
                tv_my_signature.setText(userInfo.signature);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_update:
            case R.id.tv_update_data:
                Intent intent = new Intent(MyHomePageActivity.this,DetailedPersonalDataActivity.class);
                startActivityForResult(intent,2);
                break;
            case R.id.iv_back2:
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_code_2:
            case R.id.iv_my_code:
                startActivity(new Intent(MyHomePageActivity.this,QrCodeActivity.class));
                break;
            case R.id.tv_production:
                vp_home_page.setCurrentItem(0);
                break;
            case R.id.tv_collect:
                vp_home_page.setCurrentItem(1);
                break;
            case R.id.tv_like:
                vp_home_page.setCurrentItem(2);
                break;
        }
    }
}
