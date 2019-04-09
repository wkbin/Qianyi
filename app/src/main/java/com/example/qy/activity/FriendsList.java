package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qy.R;
import com.example.qy.adapter.FriendsAdapter;
import com.example.qy.whs.BaseActivity;

public class FriendsList extends BaseActivity {
    private RecyclerView rc_friends;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        init("我的好友");

        rc_friends = findViewById(R.id.rc_friends);
        rc_friends.setLayoutManager(new LinearLayoutManager(this));
        rc_friends.setAdapter(new FriendsAdapter(this));
    }
}
