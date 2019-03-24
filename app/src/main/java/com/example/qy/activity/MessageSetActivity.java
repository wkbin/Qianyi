package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class MessageSetActivity extends BaseActivity implements View.OnClickListener {
    private ImageView action_bar_iv_left;
    private TextView action_bar_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_set);

        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_text = findViewById(R.id.action_bar_text);

        action_bar_text.setText("消息设置");
        action_bar_iv_left.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_bar_iv_left:
                finish();
                break;

        }
    }
}
