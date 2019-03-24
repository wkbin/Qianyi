package com.example.qy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.HorizontalStepsViewIndicator;
import com.baoyachi.stepview.bean.StepBean;
import com.example.qy.R;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class IntegralTaskActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_integral_description;
    private ImageView action_bar_iv_left;
    private TextView action_bar_iv_right;
    private TextView tv_sign;
    private ImageView iv_sign;

    private HorizontalStepsViewIndicator step_view;




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

        step_view = findViewById(R.id.step_view);

        List<StepBean> stepsBeanList = new ArrayList<>();
        StepBean stepBean0 = new StepBean("+10",1);
        StepBean stepBean1 = new StepBean("+10",1);
        StepBean stepBean2 = new StepBean("+10",1);
        StepBean stepBean3 = new StepBean("+10",-1);
        StepBean stepBean4 = new StepBean("+10",-1);
        StepBean stepBean5 = new StepBean("+10",-1);
        StepBean stepBean6 = new StepBean("+20",-1);
        stepsBeanList.add(stepBean0);
        stepsBeanList.add(stepBean1);
        stepsBeanList.add(stepBean2);
        stepsBeanList.add(stepBean3);
        stepsBeanList.add(stepBean4);
        stepsBeanList.add(stepBean5);
        stepsBeanList.add(stepBean6);




        step_view.setStepNum(stepsBeanList);

        step_view.setCompleteIcon(ContextCompat.getDrawable(this, R.mipmap.greenhook));
        step_view.setCompletedLineColor(Color.parseColor("#91E573"));
        step_view.setUnCompletedLineColor(Color.parseColor("#F2F2F2"));

//                .setStepsViewIndicatorCompletedLineColor(Color.parseColor("#91E573"))//设置StepsViewIndicator完成线的颜色
//                .setStepsViewIndicatorUnCompletedLineColor(Color.parseColor("#F2F2F2"))//设置StepsViewIndicator未完成线的颜色
//                .setStepViewComplectedTextColor(ContextCompat.getColor(this, android.R.color.white))//设置StepsView text完成线的颜色
//                .setStepViewUnComplectedTextColor(ContextCompat.getColor(this, R.color.uncompleted_text_color))//设置StepsView text未完成线的颜色
//                .setStepsViewIndicatorCompleteIcon(ContextCompat.getDrawable(this, R.mipmap.greenhook))//设置StepsViewIndicator CompleteIcon
//                .setStepsViewIndicatorDefaultIcon(ContextCompat.getDrawable(this, R.drawable.default_icon))//设置StepsViewIndicator DefaultIcon
//                .setStepsViewIndicatorAttentionIcon(ContextCompat.getDrawable(this, R.drawable.attention));//设置StepsViewIndicator AttentionIcon



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
