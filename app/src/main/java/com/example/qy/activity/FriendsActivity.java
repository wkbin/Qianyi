package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class FriendsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        init("好友");
    }
}
