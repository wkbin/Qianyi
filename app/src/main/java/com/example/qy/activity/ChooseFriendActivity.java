package com.example.qy.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.example.qy.R;
import com.example.qy.adapter.FriendAdapter;
import com.example.qy.bean.ChooseFriend;
import com.example.qy.whs.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ChooseFriendActivity extends BaseActivity {
    private RecyclerView section_rv;
    private FriendAdapter adapter;
    private List<ChooseFriend> list;
    private RelativeLayout rl_wnr;
    private EditText et_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_friend);
        init("选择好友");

        section_rv = findViewById(R.id.section_rv);
        rl_wnr = findViewById(R.id.rl_wnr);
        et_search = findViewById(R.id.et_search);

        list = new ArrayList<>();
        for(int j = 97 ; j < 123;j++){
            ChooseFriend friend = new ChooseFriend();
            friend.name = String.valueOf((char)j);
            list.add(friend);

        }
        for(int i = 65; i<= 90 ;i++){
             ChooseFriend friend = new ChooseFriend();
             friend.name = String.valueOf((char)i);
             list.add(friend);
        }

        adapter = new FriendAdapter(ChooseFriendActivity.this,list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        section_rv.setLayoutManager(manager);
        section_rv.setAdapter(adapter);


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0){
                    section_rv.setVisibility(View.GONE);
                    rl_wnr.setVisibility(View.VISIBLE);
                }else{
                    section_rv.setVisibility(View.VISIBLE);
                    rl_wnr.setVisibility(View.GONE);
                }
            }
        });





    }
}
