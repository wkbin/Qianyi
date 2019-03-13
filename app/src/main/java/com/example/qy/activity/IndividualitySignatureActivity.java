package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class IndividualitySignatureActivity extends BaseActivity implements View.OnClickListener {
    private TextView action_bar_text;
    private TextView tv_character_length;
    private EditText et_signature;
    private ImageView iv_clear;
    private ImageView action_bar_iv_left;
    private TextView action_bar_iv_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individuality_signature);

        action_bar_text = findViewById(R.id.action_bar_text);
        et_signature = findViewById(R.id.et_signature);
        tv_character_length = findViewById(R.id.tv_character_length);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);
        iv_clear = findViewById(R.id.iv_clear);
        iv_clear.setOnClickListener(this);
        action_bar_iv_left.setOnClickListener(this);
        action_bar_iv_right.setOnClickListener(this);
        action_bar_text.setText("修改签名");

        et_signature.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                tv_character_length.setText(s.length()+"/20字符");
                if (s.length() == 0){
                    iv_clear.setVisibility(View.GONE);
                }else{
                    iv_clear.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_clear:
                et_signature.setText(null);
                break;
            case R.id.action_bar_iv_left:
                finish();
                break;
            case R.id.action_bar_iv_right:
                // 保存
                Intent intent = new Intent();
                intent.putExtra("signature",et_signature.getText().toString().trim());
                setResult(RESULT_OK,intent);
                finish();
                break;

        }
    }
}
