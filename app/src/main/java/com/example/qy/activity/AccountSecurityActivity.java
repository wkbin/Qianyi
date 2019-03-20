package com.example.qy.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class AccountSecurityActivity extends BaseActivity implements View.OnClickListener {
    private TextView action_bar_text;
    private ImageView action_bar_iv_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);

        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_text.setText("账号与安全");
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
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
