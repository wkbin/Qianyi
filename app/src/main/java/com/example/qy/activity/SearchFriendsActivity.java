package com.example.qy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.qy.R;
import com.example.qy.adapter.FollowersAdapter;
import com.example.qy.bean.Follwers;
import com.example.qy.whs.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class SearchFriendsActivity extends BaseActivity implements View.OnClickListener{
    private RecyclerView rc_may_know;
    private List<Follwers> follwersList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);
        init("");

        rc_may_know = findViewById(R.id.rc_may_know);
        rc_may_know.setLayoutManager(new LinearLayoutManager(this));

        follwersList = new ArrayList<>();

        Follwers follwers = new Follwers();
        follwers.signature = "没个性，不签名";
        follwers.name = "测试";
        follwers.focus = "0";
        follwers.together = "0";

        follwersList.add(follwers);
        follwersList.add(follwers);
        follwersList.add(follwers);

        FollowersAdapter adapter = new FollowersAdapter(this,follwersList,2);
        rc_may_know.setAdapter(adapter);

        findViewById(R.id.tv_friends).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_friends:
                startActivity(new Intent(this,FriendsList.class));
                break;
        }
    }
}
