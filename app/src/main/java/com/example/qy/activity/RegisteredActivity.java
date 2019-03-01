package com.example.qy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qy.R;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.RegexUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.utils.UniquePsuedoUtils;
import com.example.qy.whs.BaseActivity;
import com.mob.MobSDK;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class RegisteredActivity extends BaseActivity implements View.OnClickListener {

    private EditText et_registered_phone;
    private TextView tv_registered_verification_code;
    private EditText et_registered_verification_code;
    private TextView tv_login_title;
    private Button btn_next_step;
    private String type;       // 判断是注册账号还是找回密码

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
                            new CountDownTimer(59000, 1000) {
                                @Override
                                public void onTick(long millisUntilFinished) {
                                    tv_registered_verification_code.setEnabled(false);
                                    tv_registered_verification_code.setTextColor(Color.parseColor("#FE4B67"));
                                    tv_registered_verification_code.setText(millisUntilFinished / 1000 + "后获取");
                                }
                                @Override
                                public void onFinish() {
                                    tv_registered_verification_code.setEnabled(true);
                                    tv_registered_verification_code.setTextColor(Color.parseColor("#D81B60"));
                                    tv_registered_verification_code.setText("重新获取验证码");

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
                            Intent intent = new Intent(RegisteredActivity.this, SetPassWordActivity.class);
                            intent.putExtra("phone",et_registered_phone.getText().toString().trim());
                            intent.putExtra("type",type);
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
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x003){
                Bundle bundle = msg.getData();
                String message = bundle.getString("message");
                boolean isSuc = bundle.getBoolean("isSuc");
                String phone = bundle.getString("phone");
                if (type.equals("registered")){
                    if (isSuc){
                        ToastUtils.showShort(RegisteredActivity.this,message);
                    }else{
                        String code = et_registered_verification_code.getText().toString().trim();
                        SMSSDK.submitVerificationCode("86",phone, code);
                    }
                }else if (type.equals("forget")){
                    if (!isSuc){
                        ToastUtils.showShort(RegisteredActivity.this,message);
                    }else{
                        String code = et_registered_verification_code.getText().toString().trim();
                        SMSSDK.submitVerificationCode("86",phone, code);
                    }
                }

            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered);

        initView();
    }
    private void initView(){
        // 初始化mobsdk
        MobSDK.init(this);
        // 注册一个事件接收器
        SMSSDK.registerEventHandler(eventHandler);
        type = getIntent().getStringExtra("type");

        tv_registered_verification_code = findViewById(R.id.tv_registered_verification_code);
        et_registered_verification_code = findViewById(R.id.et_registered_verification_code);

        tv_login_title = findViewById(R.id.tv_login_title);
        btn_next_step = findViewById(R.id.btn_next_step);

        et_registered_phone = findViewById(R.id.et_registered_phone);
        if (TextUtils.isEmpty(type)){
            ToastUtils.showShort(RegisteredActivity.this,"发生错误,没有获取type!");
            return;
        }else if (type.equals("registered")){
            tv_login_title.setText("注册账号");
        }else if (type.equals("forget")){
            tv_login_title.setText("找回密码");
        }else{
            ToastUtils.showShort(RegisteredActivity.this,"发生错误,没有获取type!");
            return;
        }


        tv_registered_verification_code.setOnClickListener(this);

        btn_next_step.setOnClickListener(this);



    }

//    private void showDialog(Context context){
//        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
//        dialog.setMessage("注册成功");
//        dialog.setPositiveButton("立即登录", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });
//        // 对话框外部不可取消
//        dialog.setCancelable(false);
//        dialog.show();
//    }

    @Override
    public void onClick(View v) {
        final String phone = et_registered_phone.getText().toString().trim();
        switch (v.getId()){
            case R.id.btn_next_step:
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showShort(RegisteredActivity.this,"请输入手机号");
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)){
                    ToastUtils.showShort(RegisteredActivity.this,"手机号格式错误");
                    return;
                }
                String code = et_registered_verification_code.getText().toString().trim();
                if (TextUtils.isEmpty(code)){
                    ToastUtils.showShort(RegisteredActivity.this,"请输入验证码");
                    return;
                }
                String phoneId = UniquePsuedoUtils.getUniquePsuedoID();
                String url = HttpQYUtils.getValidationLogin(phone,phoneId);
                Log.d("RegisteredActivity","url = "+url);
                HttpUtils.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) { }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            boolean isSuc = jsonObject.getBoolean("isSuc");
                            String message = jsonObject.getString("msg");
                            Message msg = new Message();
                            msg.what = 0x003;
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("isSuc",isSuc);
                            bundle.putString("message",message);
                            bundle.putString("phone",phone);
                            msg.setData(bundle);
                            handler.sendMessage(msg);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.tv_registered_verification_code:
                if (TextUtils.isEmpty(phone)){
                    Toast.makeText(RegisteredActivity.this,"请输入手机号",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!RegexUtils.isMobileExact(phone)){
                    Toast.makeText(RegisteredActivity.this,"手机号格式错误",Toast.LENGTH_SHORT).show();
                    return;
                }
                SMSSDK.getVerificationCode("86", phone);
                break;
        }
    }
}
