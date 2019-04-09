package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qy.R;
import com.example.qy.adapter.MsgAdapter;
import com.example.qy.bean.Msg;
import com.example.qy.whs.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends BaseActivity {
    private RecyclerView rc_message;
    List<Msg> msgList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        init("xx");

        initMsgs();

        rc_message = findViewById(R.id.rc_message);
        rc_message.setLayoutManager(new LinearLayoutManager(this));
        rc_message.setAdapter(new MsgAdapter(this,msgList));

        findViewById(R.id.iv_char_detail).setOnClickListener(v -> {
            startActivity(new Intent(this,ChatDetailsActivity.class));
        });
    }

    private void initMsgs(){
        Msg msg1 = new Msg("傻逼魏斌峰?",Msg.TYPE_RECEIVED);
        msgList.add(msg1);
        Msg msg2 = new Msg("没错，我就是傻逼魏斌峰!",Msg.TYPE_SENT);
        msgList.add(msg2);
        Msg msg3 = new Msg("哈哈哈哈哈",Msg.TYPE_RECEIVED);
        msgList.add(msg3);
    }
}
