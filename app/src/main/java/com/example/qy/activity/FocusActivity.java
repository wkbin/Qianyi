package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qy.R;
import com.example.qy.adapter.FollowersAdapter;
import com.example.qy.bean.Follwers;

import java.util.ArrayList;
import java.util.List;

public class FocusActivity extends AppCompatActivity {
    private RecyclerView rl_focus;
        private List<Follwers> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        rl_focus = findViewById(R.id.rl_focus);
        lists = new ArrayList<>();
        Follwers follwers = new Follwers();
        follwers.setIcon("https://pic.qqtn.com/up/2019-2/2019020508422893964.jpg");
        follwers.setName("用户1");
        follwers.setWorkAndFans("作品125 粉丝546");
        lists.add(follwers);
        LinearLayoutManager manager = new LinearLayoutManager(FocusActivity.this);
        FollowersAdapter adapter = new FollowersAdapter(FocusActivity.this,lists);
        rl_focus.setLayoutManager(manager);
        rl_focus.setAdapter(adapter);
    }
}
