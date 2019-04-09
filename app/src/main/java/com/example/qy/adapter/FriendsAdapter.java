package com.example.qy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.qy.R;
import com.example.qy.activity.ChatActivity;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 04 日 上午 9:20
 * Description:
 */
public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder> {
    public FriendsAdapter(Context context){
        this.context = context;
    }
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.friend_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.li_send_message.setOnClickListener(v -> {
            context.startActivity(new Intent(context,ChatActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout li_send_message;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            li_send_message = itemView.findViewById(R.id.li_send_message);
        }
    }
}
