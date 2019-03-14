package com.example.qy.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class MessageActivity extends BaseActivity {
    private TextView action_bar_text;
    private ImageView action_bar_iv_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //修改为深色，因为我们把状态栏的背景色修改为主题色白色，默认的文字及图标颜色为白色，导致看不到了。
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_message);

        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_text.setText("消息");

        action_bar_iv_left.setOnClickListener(v->{
            finish();
        });
    }
}
