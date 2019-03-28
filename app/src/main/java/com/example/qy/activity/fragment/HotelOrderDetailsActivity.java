package com.example.qy.activity.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class HotelOrderDetailsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_order_details);

        init("订单详情");
    }
}
