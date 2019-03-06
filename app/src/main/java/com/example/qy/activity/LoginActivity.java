package com.example.qy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qy.R;
import com.example.qy.bean.UserInfo;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.RegexUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.utils.UniquePsuedoUtils;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_password_to_login;
    private TextView tv_show_phone_message;
    private TextView tv_quick_login;
    private LinearLayout li_get_code, li_verification_code_login, li_password_to_login;
    private ImageView iv_shutdown;
    private EditText et_quick_login_phone;
    private EditText et_verification_code;
    private Button btn_get_verification_code;
    private Button btn_to_obtain;
    private Button btn_validation;
    private boolean isValidation = false;
    private String phone;
    // 用来存储用户信息
    private UserInfo userInfo;

    private EditText et_password_login_phone;
    private EditText et_password_login_pwd;
    private Button btn_password_login;
    private TextView tv_forgot_password;
    // 存储用户id，下次自动登录
    private SharedPreferences.Editor editor;

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

                            li_verification_code_login.setVisibility(View.VISIBLE);
                            li_password_to_login.setVisibility(View.GONE);
                            li_get_code.setVisibility(View.GONE);
                            isValidation = true;
                            iv_shutdown.setImageResource(R.mipmap.fanhui);


                            String head = phone.substring(0, 3);
                            String tail = phone.substring(7, 11);
                            tv_show_phone_message.setText("短信验证码已发送 +86 " + head + "****" + tail);

                            new CountDownTimer(59000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    btn_to_obtain.setClickable(false);
                                    btn_to_obtain.setText(millisUntilFinished / 1000 + "S");
                                    btn_to_obtain.setBackgroundResource(R.drawable.shape_retrieve_captcha_off);
                                    btn_to_obtain.setTextColor(Color.parseColor("#ffffff"));
                                }

                                @Override
                                public void onFinish() {
                                    btn_to_obtain.setClickable(true);
                                    btn_to_obtain.setText("重新获取");
                                    btn_to_obtain.setBackgroundResource(R.drawable.shape_retrieve_captcha_on);
                                    btn_to_obtain.setTextColor(Color.parseColor("#1a1a1a"));
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


                            if (userInfo == null){
                                // 跳转注册界面
                                Intent intent = new Intent(LoginActivity.this,SetPassWordActivity.class);
                                intent.putExtra("phone",phone);
                                intent.putExtra("type",1);
                                startActivity(intent);
                            }else{
                                // 登录成功 保存登录状态
                                MyApplication application = (MyApplication) getApplication();
                                application.setUserInfo(userInfo);

                                editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                                editor.putInt("id",userInfo.loginId);
                                editor.apply();

                                finish();
                            }

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        // 初始化mobsdk
        MobSDK.init(this);
        // 注册一个事件接收器
        SMSSDK.registerEventHandler(eventHandler);

        tv_password_to_login = findViewById(R.id.tv_password_to_login);
        tv_quick_login = findViewById(R.id.tv_quick_login);
        li_get_code = findViewById(R.id.li_get_code);
        li_verification_code_login = findViewById(R.id.li_verification_code_login);
        li_password_to_login = findViewById(R.id.li_password_to_login);
        iv_shutdown = findViewById(R.id.iv_shutdown);
        et_quick_login_phone = findViewById(R.id.et_quick_login_phone);
        btn_get_verification_code = findViewById(R.id.btn_get_verification_code);
        btn_to_obtain = findViewById(R.id.btn_to_obtain);
        tv_show_phone_message = findViewById(R.id.tv_show_phone_message);
        et_verification_code = findViewById(R.id.et_verification_code);
        btn_validation = findViewById(R.id.btn_validation);
        et_password_login_phone = findViewById(R.id.et_password_login_phone);
        et_password_login_pwd = findViewById(R.id.et_password_login_pwd);
        btn_password_login = findViewById(R.id.btn_password_login);
        tv_forgot_password = findViewById(R.id.tv_forgot_password);

        tv_password_to_login.setOnClickListener(this);
        tv_quick_login.setOnClickListener(this);
        iv_shutdown.setOnClickListener(this);
        btn_get_verification_code.setOnClickListener(this);
        btn_to_obtain.setOnClickListener(this);
        btn_validation.setOnClickListener(this);
        btn_password_login.setOnClickListener(this);
        tv_forgot_password.setOnClickListener(this);

        et_quick_login_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    btn_get_verification_code.setBackgroundResource(R.drawable.shape_login_button_on);
                    btn_get_verification_code.setTextColor(Color.parseColor("#1a1a1a"));
                    btn_get_verification_code.setClickable(true);
                } else {
                    btn_get_verification_code.setBackgroundResource(R.drawable.shape_login_button_off);
                    btn_get_verification_code.setTextColor(Color.parseColor("#ffffff"));
                    btn_get_verification_code.setClickable(false);
                }
            }
        });
        et_verification_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 4) {
                    btn_validation.setBackgroundResource(R.drawable.shape_login_button_on);
                    btn_validation.setTextColor(Color.parseColor("#1a1a1a"));
                    btn_validation.setClickable(true);
                } else {
                    btn_validation.setBackgroundResource(R.drawable.shape_login_button_off);
                    btn_validation.setTextColor(Color.parseColor("#ffffff"));
                    btn_validation.setClickable(false);
                }
            }
        });
        et_password_login_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() >= 6 && et_password_login_phone.length() == 11){
                    btn_password_login.setBackgroundResource(R.drawable.shape_login_button_on);
                    btn_password_login.setTextColor(Color.parseColor("#1a1a1a"));
                    btn_password_login.setClickable(true);
                }else{
                    btn_password_login.setBackgroundResource(R.drawable.shape_login_button_off);
                    btn_password_login.setTextColor(Color.parseColor("#ffffff"));
                    btn_password_login.setClickable(false);
                }
            }
        });
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_password_to_login:
                li_get_code.setVisibility(View.GONE);
                li_verification_code_login.setVisibility(View.GONE);
                li_password_to_login.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_quick_login:
                li_verification_code_login.setVisibility(View.GONE);
                li_password_to_login.setVisibility(View.GONE);
                li_get_code.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_shutdown:
                if (isValidation) {
                    li_password_to_login.setVisibility(View.GONE);
                    li_get_code.setVisibility(View.VISIBLE);
                    li_verification_code_login.setVisibility(View.GONE);
                    isValidation = false;
                    iv_shutdown.setImageResource(R.mipmap.guanbi);
                } else {
                    finish();
                }

                break;
            case R.id.btn_to_obtain:
            case R.id.btn_get_verification_code:
                phone = et_quick_login_phone.getText().toString().trim();
                // 获取验证码
                if (!RegexUtils.isMobileExact(phone)) {
                    ToastUtils.showShort(LoginActivity.this, "手机号格式不正确");
                    return;
                }
                SMSSDK.getVerificationCode("86", phone);
                break;
            case R.id.btn_validation:

                final String verificationCode = et_verification_code.getText().toString().trim();


                if (verificationCode.length() < 4) {
                    ToastUtils.showShort(LoginActivity.this, "请输入4位验证码");
                    return;
                }


                String phoneId = UniquePsuedoUtils.getUniquePsuedoID();
                HttpUtils.sendOkHttpRequest(HttpQYUtils.getValidationLogin(phone,phoneId), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(new Runnable() {
                            @Override
                                public void run() {
                                    ToastUtils.showShort(LoginActivity.this,"连接断开");
                                }
                            });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            boolean isSuc = jsonObject.getBoolean("isSuc");
                            if (isSuc){
                                userInfo = new UserInfo();
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                userInfo.birthday = dataObject.getString("birthday");
                                userInfo.phone = dataObject.getString("phone");
                                userInfo.signature = dataObject.getString("signature");
                                userInfo.integral = dataObject.getString("integral");
                                userInfo.sex = dataObject.getString("sex");
                                userInfo.nickname = dataObject.getString("nickname");
                                userInfo.icon = dataObject.getString("icon");
                                userInfo.manifesto = dataObject.getString("manifesto");
                                userInfo.home = dataObject.getString("home");
                                userInfo.fans = dataObject.getString("fans");
                                userInfo.qianyiID = dataObject.getString("qianyiID");
                                userInfo.follow = dataObject.getString("follow");
                            }
                            runOnUiThread(()->{
                                    SMSSDK.submitVerificationCode("86",phone, verificationCode);
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_password_login:
                String phone = et_password_login_phone.getText().toString().trim();
                String pwd = et_password_login_pwd.getText().toString().trim();

                phoneId = UniquePsuedoUtils.getUniquePsuedoID();
                HttpUtils.sendOkHttpRequest(HttpQYUtils.getLoginPassWord(phone, pwd,phoneId), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(()-> {
                                ToastUtils.showShort(LoginActivity.this,"连接断开");
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            boolean isSuc = jsonObject.getBoolean("isSuc");
                            final String msg = jsonObject.getString("msg");
                            if (isSuc){
                                userInfo = new UserInfo();
                                JSONObject dataObject = jsonObject.getJSONObject("data");
                                userInfo.birthday = dataObject.getString("birthday");
                                userInfo.phone = dataObject.getString("phone");
                                userInfo.signature = dataObject.getString("signature");
                                userInfo.integral = dataObject.getString("integral");
                                userInfo.sex = dataObject.getString("sex");
                                userInfo.nickname = dataObject.getString("nickname");
                                userInfo.icon = dataObject.getString("icon");
                                userInfo.manifesto = dataObject.getString("manifesto");
                                userInfo.home = dataObject.getString("home");
                                userInfo.fans = dataObject.getString("fans");
                                userInfo.qianyiID = dataObject.getString("qianyiID");
                                userInfo.loginId = dataObject.getInt("loginId");
                                userInfo.follow = dataObject.getString("follow");
                                Log.e("666","follow == "+userInfo.follow);
                                MyApplication application = (MyApplication) getApplication();
                                application.setUserInfo(userInfo);

                                editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                                editor.putInt("id",userInfo.loginId);
                                editor.apply();

                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.tv_forgot_password:
                // 忘记密码
                startActivity(new Intent(LoginActivity.this,RetrievePasswordActivity.class));
                break;
        }
    }


}