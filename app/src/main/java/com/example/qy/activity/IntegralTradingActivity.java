package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qy.R;
import com.example.qy.adapter.HotForAdapter;
import com.example.qy.whs.BaseActivity;

public class IntegralTradingActivity extends BaseActivity {
    private RecyclerView rc_credits_exchange;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_trading);
        init("积分商城");

        rc_credits_exchange = findViewById(R.id.rc_credits_exchange);

        rc_credits_exchange = findViewById(R.id.rc_credits_exchange);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        rc_credits_exchange.setLayoutManager(manager);
        rc_credits_exchange.setAdapter(new HotForAdapter(this));
        rc_credits_exchange.setNestedScrollingEnabled(false);
    }
}
