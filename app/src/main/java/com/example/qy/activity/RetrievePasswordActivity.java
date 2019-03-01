package com.example.qy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.qy.R;
import com.example.qy.whs.BaseActivity;
import com.mob.MobSDK;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 找回密码
 */
public class RetrievePasswordActivity extends BaseActivity implements View.OnClickListener {
    private ImageView set_pwd_back;
    private ImageView iv_clear_phone;
    private EditText et_get_phone;
    private EditText et_get_code;
    private Button btn_next;
    private Button btn_get_code;
    private String phone;

    private EventHandler eventHandler = new EventHandler() {
        public void afterEvent(int event, int result, Object data) {
            // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
            Message msg = new Message();
            msg.arg1 = event;
            msg.arg2 = result;
            msg.obj = data;
            new Handler(Looper.getMainLooper(), new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    int event = msg.arg1;
                    int result = msg.arg2;
                    Object data = msg.obj;
                    if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理成功得到验证码的结果
                            // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                            Log.d("LoginActivity", "获取成功");


                            new CountDownTimer(49000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    btn_get_code.setClickable(false);
                                    btn_get_code.setText(millisUntilFinished / 1000 + "S重新获取");
                                    btn_get_code.setBackgroundResource(R.drawable.shape_get_code_off);
                                    btn_get_code.setTextColor(Color.parseColor("#B3B3B3"));
                                }

                                @Override
                                public void onFinish() {
                                    btn_get_code.setClickable(true);
                                    btn_get_code.setText("获取验证码");
                                    btn_get_code.setBackgroundResource(R.drawable.shape_get_code_on);
                                    btn_get_code.setTextColor(Color.parseColor("#666666"));
                                }
                            }.start();
                        } else {
                            // TODO 处理错误的结果
                            Log.d("LoginActivity", "获取失败");
                            ((Throwable) data).printStackTrace();
                        }
                    } else if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            // TODO 处理验证码验证通过的结果
                            Log.d("LoginActivity", "验证成功");


                            Intent intent = new Intent(RetrievePasswordActivity.this,SetPassWordActivity.class);
                            intent.putExtra("phone",phone);
                            intent.putExtra("type",0);
                            startActivity(intent);


                        } else {
                            // TODO 处理错误的结果
                            Log.d("LoginActivity", "验证失败");
                            Toast.makeText(getApplicationContext(), "验证码错误", Toast.LENGTH_SHORT).show();
                            ((Throwable) data).printStackTrace();
                        }
                    }
                    // TODO 其他接口的返回结果也类似，根据event判断当前数据属于哪个接口
                    return false;
                }
            }).sendMessage(msg);
        }
    };
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);

        initView();
    }
    private void initView(){

        // 初始化mobsdk
        MobSDK.init(this);
        // 注册一个事件接收器
        SMSSDK.registerEventHandler(eventHandler);

        set_pwd_back = findViewById(R.id.set_pwd_back);
        iv_clear_phone = findViewById(R.id.iv_clear_phone);
        et_get_phone = findViewById(R.id.et_get_phone);
        et_get_code = findViewById(R.id.et_get_code);
        btn_next = findViewById(R.id.btn_next);
        btn_get_code = findViewById(R.id.btn_get_code);

        set_pwd_back.setOnClickListener(this);
        iv_clear_phone.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        btn_get_code.setOnClickListener(this);


        et_get_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    iv_clear_phone.setVisibility(View.VISIBLE);
                }else{
                    iv_clear_phone.setVisibility(View.GONE);
                }
                if (s.length() == 11 && et_get_code.getText().toString().length() == 4 ){
                    btn_next.setBackgroundResource(R.drawable.shape_settings_pwd_on);
                    btn_next.setClickable(true);
                    btn_next.setTextColor(Color.parseColor("#ffffff"));
                }else{
                    btn_next.setBackgroundResource(R.drawable.shape_settings_pwd_off);
                    btn_next.setClickable(false);
                    btn_next.setTextColor(Color.parseColor("#999999"));
                }
            }
        });
        et_get_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4 && et_get_phone.getText().toString().length() == 11 ){
                    btn_next.setBackgroundResource(R.drawable.shape_settings_pwd_on);
                    btn_next.setClickable(true);
                    btn_next.setTextColor(Color.parseColor("#ffffff"));
                }else{
                    btn_next.setBackgroundResource(R.drawable.shape_settings_pwd_off);
                    btn_next.setClickable(false);
                    btn_next.setTextColor(Color.parseColor("#999999"));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_pwd_back:
                finish();
                break;
            case R.id.iv_clear_phone:
                et_get_phone.setText("");
                break;
            case R.id.btn_next:
                // 下一步
                phone = et_get_phone.getText().toString().trim();
                SMSSDK.submitVerificationCode("86",phone, et_get_code.getText().toString().trim());
                break;
            case R.id.btn_get_code:
                // 发送验证码
                phone = et_get_phone.getText().toString().trim();
                SMSSDK.getVerificationCode("86", phone);
                break;
        }
    }
    // 使用完EventHandler需注销，否则可能出现内存泄漏
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    protected void onStop() {
        super.onStop();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SMSSDK.registerEventHandler(eventHandler);
    }
}
