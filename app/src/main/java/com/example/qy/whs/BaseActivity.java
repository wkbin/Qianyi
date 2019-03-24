package com.example.qy.whs;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.activity.LoginActivity;
import com.example.qy.bean.UserInfo;
import com.jaeger.library.StatusBarUtil;

import java.util.Timer;
import java.util.TimerTask;

public class BaseActivity extends AppCompatActivity {
    private UserInfo userInfo;
    private TextView action_bar_text;
    private ImageView action_bar_iv_left;


    public UserInfo getUserInfo() {
        return userInfo;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucent(BaseActivity.this);
        // 强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 默认黑色图标
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);

        MyApplication application = (MyApplication) getApplication();
        userInfo = application.getUserInfo();

    }
    public void init(String str){
        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_text.setText(str);
        action_bar_iv_left.setOnClickListener(v->{
            finish();
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setStatusBar();
    }
    protected void setStatusBar(){
//        StatusBarUtil.setColor(this,getResources().getColor(R.color.qy_white));
    }

}
