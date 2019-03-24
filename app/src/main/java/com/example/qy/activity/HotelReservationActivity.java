package com.example.qy.activity;


import android.os.Bundle;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class HotelReservationActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_reservation);

        init("酒店订单");
    }
}
