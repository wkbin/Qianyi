package com.example.qy.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import com.example.qy.R;
import com.example.qy.whs.ActivityCollector;
import com.example.qy.whs.BaseActivity;
import com.jaeger.library.StatusBarUtil;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setColor(this,getResources().getColor(R.color.qy_white));
        setContentView(R.layout.activity_settings);

    }


}
