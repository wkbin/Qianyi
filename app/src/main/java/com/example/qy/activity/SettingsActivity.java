package com.example.qy.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.ActivityCollector;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;
import com.jaeger.library.StatusBarUtil;

public class SettingsActivity extends BaseActivity implements View.OnClickListener {
    private TextView action_bar_text;
    private TextView action_bar_iv_right;
    private ImageView action_bar_iv_left;
    private TextView tv_change_the_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.qy_white));
        setContentView(R.layout.activity_settings);

        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_text.setText("设置");
        action_bar_iv_right.setVisibility(View.INVISIBLE);

        tv_change_the_account = findViewById(R.id.tv_change_the_account);
        tv_change_the_account.setOnClickListener(this);
        action_bar_iv_left.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_bar_iv_left:
                finish();
                break;
            case R.id.tv_change_the_account:
                // 更换账号,清空保存id
                AlertDialog.Builder normalDialog = new AlertDialog.Builder(SettingsActivity.this);
                normalDialog.setMessage("更换账号将清除登录信息?");
                normalDialog.setPositiveButton("确定", (dialog,which)-> {
                    SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.clear();
                    if (editor.commit()){
                        ActivityCollector.finaishAll();
                        MyApplication application = (MyApplication) getApplication();
                        application.setUserInfo(null);
                        startActivity(new Intent(this,MainActivity.class));
                    }
                });
                normalDialog.setNegativeButton("关闭",(dialog,which)-> {

                });
                // 显示
                normalDialog.show();
                break;
        }
    }
}
