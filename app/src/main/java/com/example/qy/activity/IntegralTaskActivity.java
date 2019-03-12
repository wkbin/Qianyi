package com.example.qy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
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

public class IntegralTaskActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_integral_description;
    private ImageView action_bar_iv_left;
    private TextView action_bar_iv_right;
    private TextView tv_sign;
    private ImageView iv_sign;


    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_task);

        initView();
        initData();
    }
    private void initView(){
        tv_integral_description = findViewById(R.id.tv_integral_description);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);

        tv_sign = findViewById(R.id.tv_sign);
        iv_sign = findViewById(R.id.iv_sign);

        action_bar_iv_left.setOnClickListener(this);
        tv_integral_description.setOnClickListener(this);
        action_bar_iv_right.setOnClickListener(this);

        tv_sign.setOnClickListener(this);
    }
    private void initData(){
        id = ((MyApplication)getApplication()).getUserInfo().loginId;
        HttpUtils.sendOkHttpRequest(HttpQYUtils.getJudgeDailyAttendance(id, "1"), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort(IntegralTaskActivity.this,"网络中断");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                try {
                    boolean isSuc = new JSONObject(responseText).getBoolean("isSuc");
                    if (!isSuc){
                        runOnUiThread(()-> {
                            tv_sign.setVisibility(View.GONE);
                            iv_sign.setVisibility(View.VISIBLE);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
            case R.id.tv_integral_description:
                startActivity(new Intent(IntegralTaskActivity.this,IntegralDescriptionActivity.class));
                break;
            case R.id.action_bar_iv_right:
                startActivity(new Intent(IntegralTaskActivity.this,IntegralSubsidiaryActivity.class));
                break;
            case R.id.tv_sign:
                HttpUtils.sendOkHttpRequest(HttpQYUtils.getSignin(id, "10", "1"), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(()->{
                            ToastUtils.showShort(IntegralTaskActivity.this,"网络断开");
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            boolean isSuc = jsonObject.getBoolean("isSuc");
                            String msg = jsonObject.getString("msg");
                            if (isSuc){
                                runOnUiThread(()->{
                                    tv_sign.setVisibility(View.GONE);
                                    iv_sign.setVisibility(View.VISIBLE);
                                });

                            }
                            runOnUiThread(()->{
                                ToastUtils.showShort(IntegralTaskActivity.this,msg);
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
