package com.example.qy.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.qy.R;
import com.example.qy.bean.UserInfo;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(()-> {

                SharedPreferences pref = getSharedPreferences("data",MODE_PRIVATE);

                int id = pref.getInt("id",-1);

                Log.d("666","id == "+id);
                if (id != -1){
                    String url = HttpQYUtils.getFindPersonalnfoWithId(id);
                    Log.d("MainActivity","url == "+url);
                    HttpUtils.sendOkHttpRequest(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(()->{
                                ToastUtils.showShort(SplashActivity.this,"网络连接失败");
                            });
                        }
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(responseText);
                                boolean isSuc = jsonObject.getBoolean("isSuc");
                                if (isSuc){
                                    UserInfo userInfo = new UserInfo();
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
                                    MyApplication application = (MyApplication) getApplication();
                                    application.setUserInfo(userInfo);
                                    runOnUiThread(()->{
                                        ToastUtils.showShort(SplashActivity.this,"登录成功");
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();

        },2000);


    }
}
