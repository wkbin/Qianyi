package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class BlacklistActivity extends BaseActivity {
    private ImageView action_bar_iv_left;
    private TextView action_bar_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blacklist);

        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_iv_left.setOnClickListener(v->{
            finish();
        });
        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_text.setText("黑名单");
    }
}
