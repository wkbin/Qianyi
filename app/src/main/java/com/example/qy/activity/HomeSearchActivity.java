package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.adapter.HistoryLabelAdapter;
import com.example.qy.whs.BaseActivity;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class HomeSearchActivity extends BaseActivity {
    private RecyclerView rc_history_label;
    private RecyclerView rc_hot_search;
    private HistoryLabelAdapter adapter;
    private FlexboxLayoutManager manager,manager2;
    private TextView tv_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_search);


        List<String> list = new ArrayList<>();
        list.add("庐山石门涧");
        list.add("西海温泉");
        list.add("三叠泉景区");
        list.add("茶园");
        list.add("仙人洞");
        list.add("茶园");
        list.add("庐山石门涧");
        list.add("西海温泉");
        list.add("三叠泉景区");
        list.add("茶园");
        list.add("仙人洞");
        list.add("三叠泉景区");
        list.add("茶园");
        list.add("仙人洞");


        rc_history_label = findViewById(R.id.rc_history_label);
        rc_hot_search = findViewById(R.id.rc_hot_search);

        adapter = new HistoryLabelAdapter(HomeSearchActivity.this,list);


        //设置布局管理器
        manager = new FlexboxLayoutManager(HomeSearchActivity.this);
        rc_history_label.setLayoutManager(manager);

        manager2 = new FlexboxLayoutManager(HomeSearchActivity.this);
        rc_hot_search.setLayoutManager(manager2);

        rc_history_label.setAdapter(adapter);
        rc_hot_search.setAdapter(adapter);

        tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(v->{
            finish();
        });
    }
}
