package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.qy.R;
import com.example.qy.adapter.MessageAdapter;
import com.example.qy.bean.Message;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.nikhilpanju.recyclerviewenhanced.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.List;

public class DirectMessagesActivity extends BaseActivity {
    private RecyclerView rl_direct_message_list;
    private RecyclerTouchListener onTouchListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_messages);
        init("私信");

        rl_direct_message_list = findViewById(R.id.rl_direct_message_list);

        List list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(new Message());
        }

        LinearLayoutManager manager = new LinearLayoutManager(DirectMessagesActivity.this);
        MessageAdapter adapter = new MessageAdapter(DirectMessagesActivity.this,list);
        rl_direct_message_list.setLayoutManager(manager);
        rl_direct_message_list.setAdapter(adapter);

        onTouchListener = new RecyclerTouchListener(this, rl_direct_message_list);

        onTouchListener.setSwipeOptionViews(R.id.delete)
                .setSwipeable(R.id.rowFG, R.id.rowBG, (viewID,position)-> {

                    if (viewID == R.id.delete) {
                        // Do something
                        ToastUtils.showShort(DirectMessagesActivity.this,"delete "+position);
                    }

                });
    }
    @Override
    protected void onResume() {
        super.onResume();
        rl_direct_message_list.addOnItemTouchListener(onTouchListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        rl_direct_message_list.removeOnItemTouchListener(onTouchListener);
    }
}
