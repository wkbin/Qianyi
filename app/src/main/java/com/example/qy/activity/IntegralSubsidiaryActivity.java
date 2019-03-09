package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.adapter.IntegralAdapter;
import com.example.qy.bean.Integral;
import com.example.qy.bean.UserInfo;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IntegralSubsidiaryActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView action_bar_iv_left;
    private TextView action_bar_text;
    private TextView action_bar_iv_right;
    private TextView tv_user_name;
    private RecyclerView rv_integral_details;
    private List<Integral> list;

    private de.hdodenhof.circleimageview.CircleImageView civ_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_subsidiary);

        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);
        tv_user_name = findViewById(R.id.tv_user_name);
        action_bar_iv_right.setVisibility(View.INVISIBLE);
        action_bar_text.setText("我的积分");

        civ_icon = findViewById(R.id.civ_icon);
        rv_integral_details = findViewById(R.id.rv_integral_details);

        action_bar_iv_left.setOnClickListener(this);

        initData();
    }
    private void initData(){
        UserInfo userInfo = ((MyApplication)getApplication()).getUserInfo();
        Glide.with(IntegralSubsidiaryActivity.this).load(userInfo.icon).into(civ_icon);
        tv_user_name.setText("尊敬的"+userInfo.nickname+"您好");

        list = new ArrayList<>();
        String url = HttpQYUtils.getFindByIntegrals(((MyApplication) getApplication()).getUserInfo().loginId);
        Log.d("666","url = "+url);
        HttpUtils.sendOkHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(()->{
                    ToastUtils.showShort(IntegralSubsidiaryActivity.this,"网络连接断开");
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                try {
                    JSONObject object = new JSONObject(responseText);
                    JSONArray array = object.getJSONObject("data").getJSONArray("integrals");
                    for (int i = 0 ; i < array.length() ; i++){
                        JSONObject integralObject = array.getJSONObject(i);
                        Integral integral = new Integral();
                        integral.integralType = integralObject.getInt("integralType");
                        integral.integralNumber = integralObject.getInt("integralNumber");
                        integral.integralTime = integralObject.getString("integralTime");
                        list.add(integral);
                    }
                    runOnUiThread(()->{
                        LinearLayoutManager manager = new LinearLayoutManager(IntegralSubsidiaryActivity.this);
                        rv_integral_details.setLayoutManager(manager);
                        rv_integral_details.setAdapter(new IntegralAdapter(IntegralSubsidiaryActivity.this,list));
                    });
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
        }
    }
}
