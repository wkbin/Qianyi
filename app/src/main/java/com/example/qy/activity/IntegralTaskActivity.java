package com.example.qy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;

public class IntegralTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_integral_description;
    private ImageView action_bar_iv_left;
    private TextView action_bar_iv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_task);

        initView();

        initData();
    }
    private void initView(){
        tv_integral_description = findViewById(R.id.tv_integral_description);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);
        action_bar_iv_left.setOnClickListener(this);
        tv_integral_description.setOnClickListener(this);
        action_bar_iv_right.setOnClickListener(this);
    }
    private void initData(){

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_bar_iv_left:
                finish();
                break;
            case R.id.tv_integral_description:
                startActivity(new Intent(IntegralTaskActivity.this,IntegralDescriptionActivity.class));
                break;
            case R.id.action_bar_iv_right:
                startActivity(new Intent(IntegralTaskActivity.this,IntegralSubsidiaryActivity.class));
                break;
        }
    }
}
