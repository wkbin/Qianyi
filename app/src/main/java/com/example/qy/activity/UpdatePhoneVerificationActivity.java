package com.example.qy.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class UpdatePhoneVerificationActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_verification;
    private TextView tv_code1,tv_code2,tv_code3,tv_code4;
    private Button btn_next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone_verification);

        init("绑定手机");

        et_verification = findViewById(R.id.et_verification);
        tv_code1 = findViewById(R.id.tv_code1);
        tv_code2 = findViewById(R.id.tv_code2);
        tv_code3 = findViewById(R.id.tv_code3);
        tv_code4 = findViewById(R.id.tv_code4);
        btn_next = findViewById(R.id.btn_next);

        // 隐藏EditText输入的文本
        et_verification.setTransformationMethod(PasswordTransformationMethod.getInstance());
        et_verification.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 4){
                    btn_next.setTextColor(Color.parseColor("#999999"));
                    btn_next.setBackgroundResource(R.drawable.shape_settings_pwd_off);
                }else{
                    btn_next.setTextColor(Color.parseColor("#ffffff"));
                    btn_next.setBackgroundResource(R.drawable.shape_settings_pwd_on);
                }
                switch (s.length()){
                    case 0:
                        tv_code1.setText("");
                        tv_code2.setText("");
                        tv_code3.setText("");
                        tv_code4.setText("");
                        break;
                    case 1:
                        tv_code1.setText(String.valueOf(s.charAt(0)));
                        tv_code2.setText("");
                        tv_code3.setText("");
                        tv_code4.setText("");
                        break;
                    case 2:
                        tv_code2.setText(String.valueOf(s.charAt(1)));
                        tv_code3.setText("");
                        tv_code4.setText("");
                        break;
                    case 3:
                        tv_code3.setText(String.valueOf(s.charAt(2)));
                        tv_code4.setText("");
                        break;
                    case 4:
                        tv_code4.setText(String.valueOf(s.charAt(3)));
                        break;
                }

            }
        });


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
