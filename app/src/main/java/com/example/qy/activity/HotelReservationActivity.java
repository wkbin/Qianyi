package com.example.qy.activity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.qy.R;
import com.example.qy.activity.fragment.HotelOrderDetailsActivity;
import com.example.qy.whs.BaseActivity;

public class HotelReservationActivity extends BaseActivity {
    private ImageView iv_alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservation);

        init("酒店订单");

        iv_alert = findViewById(R.id.iv_alert);
        iv_alert.setOnClickListener(v->{
            startActivity(new Intent(this,HotelOrderDetailsActivity.class));
        });
    }
}
