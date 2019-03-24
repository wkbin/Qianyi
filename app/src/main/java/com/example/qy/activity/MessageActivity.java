package com.example.qy.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.adapter.MallHotAdapter;
import com.example.qy.adapter.MessageAdapter;
import com.example.qy.bean.Message;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout li_like;
    private LinearLayout li_comments;
    private LinearLayout li_direct_messages;
    private LinearLayout li_friends;
    private RecyclerView rl_message_list;
    private List<Message> list;

    private RecyclerTouchListener onTouchListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        init("消息");
        li_like = findViewById(R.id.li_like);
        li_comments = findViewById(R.id.li_comments);
        li_direct_messages = findViewById(R.id.li_direct_messages);
        li_friends = findViewById(R.id.li_friends);
        rl_message_list = findViewById(R.id.rl_message_list);
        li_like.setOnClickListener(this);
        li_comments.setOnClickListener(this);
        li_direct_messages.setOnClickListener(this);
        li_friends.setOnClickListener(this);

        list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(new Message());
        }




        LinearLayoutManager manager = new LinearLayoutManager(MessageActivity.this);
        MessageAdapter adapter = new MessageAdapter(MessageActivity.this,list);
        rl_message_list.setLayoutManager(manager);
        rl_message_list.setAdapter(adapter);

        onTouchListener = new RecyclerTouchListener(this, rl_message_list);

        onTouchListener.setSwipeOptionViews(R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, (viewID,position)-> {

                        if (viewID == R.id.delete) {
                            // Do something
                            ToastUtils.showShort(MessageActivity.this,"delete "+position);
                        }

                });


    }

    @Override
    protected void onResume() {
        super.onResume();
        rl_message_list.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        rl_message_list.removeOnItemTouchListener(onTouchListener);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_like:
                startActivity(new Intent(MessageActivity.this,LikeActivity.class));
                break;
            case R.id.li_comments:
                startActivity(new Intent(MessageActivity.this,CommentsActivity.class));
                break;
            case R.id.li_direct_messages:
                startActivity(new Intent(MessageActivity.this,DirectMessagesActivity.class));
                break;
            case R.id.li_friends:
                startActivity(new Intent(MessageActivity.this,FriendsActivity.class));
                break;
        }
    }
}
