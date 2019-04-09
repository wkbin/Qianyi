package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.adapter.HotForAdapter;
import com.example.qy.whs.BaseActivity;

public class IntegralMallActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_lucky_draw;
    private RecyclerView rc_hot_for;
    private LinearLayout li_more;
    private LinearLayout li_get_integral;
    private RelativeLayout rl_my_integral;
    private TextView action_bar_tv_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_mall);


        init("积分商城");

        iv_lucky_draw = findViewById(R.id.iv_lucky_draw);
        li_more = findViewById(R.id.li_more);
        li_get_integral = findViewById(R.id.li_get_integral);
        iv_lucky_draw.setOnClickListener(this);
        li_more.setOnClickListener(this);
        li_get_integral.setOnClickListener(this);

        rc_hot_for = findViewById(R.id.rc_hot_for);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        rc_hot_for.setLayoutManager(manager);
        rc_hot_for.setAdapter(new HotForAdapter(this));
        rc_hot_for.setNestedScrollingEnabled(false);

        action_bar_tv_right = findViewById(R.id.action_bar_tv_right);
        action_bar_tv_right.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_lucky_draw:
                startActivity(new Intent(this,IntegralDrawActivity.class));
                break;
            case R.id.li_more:
                startActivity(new Intent(this,IntegralTradingActivity.class));
                break;
            case R.id.li_get_integral:
                startLoading();
                break;
            case R.id.rl_my_integral:
                stopLoading();
                break;
            case R.id.action_bar_tv_right:
                startActivity(new Intent(this,MyAssetsActivity.class));
                break;
        }
    }
}
