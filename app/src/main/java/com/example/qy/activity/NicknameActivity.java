package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.ui.SexChooseDialog;
import com.example.qy.utils.ToastUtils;

public class NicknameActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView action_bar_text;
    private TextView tv_character_length;
    private EditText et_nickname;
    private ImageView iv_clear;
    private ImageView action_bar_iv_left;
    private TextView action_bar_iv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nickname);

        action_bar_text = findViewById(R.id.action_bar_text);
        tv_character_length = findViewById(R.id.tv_character_length);
        et_nickname = findViewById(R.id.et_nickname);
        iv_clear = findViewById(R.id.iv_clear);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);
        action_bar_text.setText("修改昵称");

        action_bar_iv_left.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
        action_bar_iv_right.setOnClickListener(this);



        et_nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                tv_character_length.setText(s.length()+"/8字符");
                if (s.length() == 0){
                    iv_clear.setVisibility(View.GONE);
                }else{
                    iv_clear.setVisibility(View.VISIBLE);
                }
            }
        });
        et_nickname.setText(getIntent().getStringExtra("name"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_bar_iv_left:
                finish();
                break;
            case R.id.iv_clear:
                et_nickname.setText(null);
                break;
            case R.id.action_bar_iv_right:
                String name = et_nickname.getText().toString();
                if (TextUtils.isEmpty(name)){
                    ToastUtils.showShort(NicknameActivity.this,"昵称不能为空");
                    return;
                }
                // 保存
                Intent intent = new Intent();
                intent.putExtra("nickname",name);
                setResult(RESULT_OK,intent);
                finish();
                break;
        }
    }
}
