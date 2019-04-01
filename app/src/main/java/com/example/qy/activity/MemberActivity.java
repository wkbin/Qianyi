package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class MemberActivity extends BaseActivity {
    private ProgressBar pb_exp;
    private TextView tv_class;
    private ImageView action_bar_iv_left;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);

        pb_exp = findViewById(R.id.pb_exp);
        pb_exp.setMax(3000);
        pb_exp.setProgress(1420);

        tv_class = findViewById(R.id.tv_class);
        tv_class.setOnClickListener(v -> {
            startActivity(new Intent(this,ClassRulesActivity.class));
        });

        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_iv_left.setOnClickListener(v -> {
            finish();
        });
    }
}
