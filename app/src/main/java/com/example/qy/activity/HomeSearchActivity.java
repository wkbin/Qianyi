package com.example.qy.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.qy.R;
import com.example.qy.adapter.HistoryLabelAdapter;
import com.example.qy.adapter.HomeSearchAdapter;
import com.example.qy.whs.BaseActivity;
import com.google.android.flexbox.FlexboxLayoutManager;
import java.util.ArrayList;
import java.util.List;

public class HomeSearchActivity extends BaseActivity {
    private RecyclerView rc_history_label;
    private RecyclerView rc_hot_search;
    private HistoryLabelAdapter adapter;
    private FlexboxLayoutManager manager,manager2;
    private TextView tv_cancel;
    private EditText et_search;
    private RecyclerView rc_search_record;
    private LinearLayout li_record;
    private HomeSearchAdapter adapter2;
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
        et_search = findViewById(R.id.et_search);
        li_record = findViewById(R.id.li_record);
        rc_search_record = findViewById(R.id.rc_search_record);

        adapter2 = new HomeSearchAdapter(this);
        rc_search_record.setLayoutManager(new LinearLayoutManager(this));
        rc_search_record.setAdapter(adapter2);

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())){
                    li_record.setVisibility(View.VISIBLE);
                    rc_search_record.setVisibility(View.GONE);
                }else{
                    rc_search_record.setVisibility(View.VISIBLE);
                    li_record.setVisibility(View.GONE);
                }
            }
        });

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
