package com.example.qy.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class SetPassWordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView set_pwd_back;
    private EditText et_set_password_1;
    private EditText et_set_password_2;
    private Button btn_set_password_complete;
    private TextView tv_title_set_password;
    private String phone;
    /**
     * 0 修改密码
     * 1 注册登录
     * 2 值没传过来
     */
    private int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_passwrod);

        initView();
    }

    private void initView(){
        phone = getIntent().getStringExtra("phone");
        type = getIntent().getIntExtra("type",2);

        set_pwd_back = findViewById(R.id.set_pwd_back);
        et_set_password_1 = findViewById(R.id.et_set_password_1);
        et_set_password_2 = findViewById(R.id.et_set_password_2);
        btn_set_password_complete = findViewById(R.id.btn_set_password_complete);
        tv_title_set_password = findViewById(R.id.tv_title_set_password);

        et_set_password_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 6 &&  et_set_password_1.getText().toString().length()>=6){
                    btn_set_password_complete.setBackgroundResource(R.drawable.shape_settings_pwd_on);
                    btn_set_password_complete.setTextColor(Color.parseColor("#ffffff"));
                    btn_set_password_complete.setClickable(true);
                }else{
                    btn_set_password_complete.setBackgroundResource(R.drawable.shape_settings_pwd_off);
                    btn_set_password_complete.setTextColor(Color.parseColor("#999999"));
                    btn_set_password_complete.setClickable(true);
                }
            }
        });

        if (type == 0){
            tv_title_set_password.setText("设置新密码");
            btn_set_password_complete.setText("提交");
        }else if (type == 1){
            tv_title_set_password.setText("设置密码");
            btn_set_password_complete.setText("完成");

        }else{
            ToastUtils.showShort(SetPassWordActivity.this,"出现错误，未获取到type");
            return;
        }


        set_pwd_back.setOnClickListener(this);
        btn_set_password_complete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_pwd_back:
                finish();
                break;
            case R.id.btn_set_password_complete:
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showShort(SetPassWordActivity.this,"未知错误,请返回重试");
                    return;
                }
                String pwd1 = et_set_password_1.getText().toString().trim();
                String pwd2 = et_set_password_2.getText().toString().trim();
                if (!pwd1.equals(pwd2)){
                    ToastUtils.showShort(SetPassWordActivity.this,"密码不一致");
                    return;
                }
                String url = null;
                if (type == 0){
                    url = HttpQYUtils.getChangePassword(phone,pwd2);
                }else if (type == 1){
                    url = HttpQYUtils.getRegistered(phone, pwd2);
                }
                HttpUtils.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(()-> {
                                ToastUtils.showShort(SetPassWordActivity.this,"网络断开");
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            boolean isSuc = jsonObject.getBoolean("isSuc");
                            final String msg = jsonObject.getString("msg");
                           runOnUiThread(() ->{
                                   ToastUtils.showShort(SetPassWordActivity.this,msg);
                           });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
    }
}
