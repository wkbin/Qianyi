package com.example.qy.activity;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qy.R;
import com.example.qy.adapter.LikeAdapter;
import com.example.qy.whs.BaseActivity;

public class CommentsActivity extends BaseActivity {
    private RecyclerView rc_comments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        init("评论");

        rc_comments = findViewById(R.id.rc_comments);
        rc_comments.setLayoutManager(new LinearLayoutManager(this));
        rc_comments.setAdapter(new LikeAdapter(this));

    }
}
