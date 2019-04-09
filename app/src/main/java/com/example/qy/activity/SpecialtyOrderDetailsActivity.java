package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qy.R;
import com.example.qy.activity.fragment.special.adapter.ContentAdapter;
import com.example.qy.whs.BaseActivity;

public class SpecialtyOrderDetailsActivity extends BaseActivity {
    private RecyclerView rc_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_order_details);
        init("订单详情");

        rc_content = findViewById(R.id.rc_content);
        rc_content.setLayoutManager(new LinearLayoutManager(this));
        rc_content.setAdapter(new ContentAdapter(this));
    }
}
