package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;

public class UpdatePhoneActivity extends AppCompatActivity {
    private TextView tv_phone;
    private ImageView action_bar_iv_left;
    private TextView action_bar_text;
    private TextView action_bar_iv_right;
    private TextView tv_update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);

        String phone = getIntent().getStringExtra("phone");
        tv_phone = findViewById(R.id.tv_phone);
        String head = phone.substring(0, 3);
        String tail = phone.substring(7, 11);
        tv_phone.setText(head+"*****"+tail);

        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);

        action_bar_iv_right.setVisibility(View.INVISIBLE);
        action_bar_iv_left.setOnClickListener(v-> {
            finish();
        });

        action_bar_text.setText("修改手机号");

        tv_update = findViewById(R.id.tv_update);

        tv_update.setOnClickListener(v->{
            startActivity(new Intent(UpdatePhoneActivity.this,UpdatePhoneVerificationActivity.class));
        });
    }
}
