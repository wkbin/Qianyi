package com.example.qy.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qy.R;
import com.example.qy.adapter.LikeAdapter;
import com.example.qy.whs.BaseActivity;

public class LikeActivity extends BaseActivity {
    private RecyclerView rc_like;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_like);

        init("赞");

        rc_like = findViewById(R.id.rc_like);
        rc_like.setLayoutManager(new LinearLayoutManager(this));
        rc_like.setAdapter(new LikeAdapter(this));
    }
}
