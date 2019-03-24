package com.example.qy.activity;


import android.os.Bundle;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class CommentsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        init("评论");
    }
}
