package com.example.qy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.whs.BaseActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UserAgreementActivity extends BaseActivity {
    private TextView tv_user_agreement;
    private TextView tv_login_title;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0x005){
                tv_user_agreement.setText(msg.obj.toString());
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agreement);

        tv_user_agreement = findViewById(R.id.tv_user_agreement);
        tv_login_title = findViewById(R.id.tv_login_title);
        tv_login_title.setText("用户协议");
        String url = HttpQYUtils.getUser_agreement();
        Log.d("UserAgreementActivity","url = "+url);
        HttpUtils.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Message message = new Message();
                message.what = 0x005;
                message.obj = responseText;
                handler.sendMessage(message);
            }
        });
    }
}
