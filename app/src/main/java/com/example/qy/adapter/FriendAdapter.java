package com.example.qy.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.bean.ChooseFriend;

import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 22 日 上午 10:23
 * Description: 好友Adapter
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder>{
    private Context context;
    private List<ChooseFriend> list;
    public FriendAdapter(Context context,List<ChooseFriend> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ChooseFriend friend = list.get(i);
        viewHolder.tv_name.setText(friend.name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
