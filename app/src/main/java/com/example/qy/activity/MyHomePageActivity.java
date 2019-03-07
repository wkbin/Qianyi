package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.bean.UserInfo;
import com.example.qy.whs.MyApplication;
import com.jaeger.library.StatusBarUtil;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyHomePageActivity extends AppCompatActivity implements View.OnClickListener {
    private CircleImageView cv_my_icon;
    private TextView tv_my_nickname;
    private TextView tv_my_id;
    private TextView tv_my_signature;
    private TextView tv_update_data;
    private ImageView iv_back;
    private ImageView iv_my_code;
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
        iv_my_code = findViewById(R.id.iv_my_code);

        tv_update_data.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_my_code.setOnClickListener(this);

    }
    private void initData(){
        MyApplication application = (MyApplication) getApplication();
        UserInfo userInfo = application.getUserInfo();
        Glide.with(MyHomePageActivity.this).load(userInfo.icon).into(cv_my_icon);
        tv_my_nickname.setText(userInfo.nickname);
        tv_my_id.setText("千艺号："+userInfo.qianyiID);
        tv_my_signature.setText(userInfo.signature);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_update_data:
                startActivity(new Intent(MyHomePageActivity.this,DetailedPersonalDataActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_my_code:
                startActivity(new Intent(MyHomePageActivity.this,QrCodeActivity.class));
                break;
        }
    }
}
