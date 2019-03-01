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

public class SettingsActivity extends BaseActivity {
    private Button btn_cancellation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        btn_cancellation = findViewById(R.id.btn_cancellation);
        btn_cancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getSharedPreferences("user",MODE_PRIVATE).edit();
                editor.clear();
                if (editor.commit()){
                    showDialog(SettingsActivity.this);
                }
            }
        });
    }

        private void showDialog(Context context){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setMessage("更换账号将清除登录记录");
        dialog.setPositiveButton("确认更换", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCollector.finaishAll();
                startActivity(new Intent(SettingsActivity.this,LoginActivity.class));
            }
        });
        dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialog.show();
    }
}
