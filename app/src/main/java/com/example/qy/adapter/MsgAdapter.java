package com.example.qy.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.bean.Msg;
import com.example.qy.whs.MyApplication;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 04 日 上午 11:20
 * Description: 消息Adapter
 */
public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder> {
    public Context context;
    List<Msg> msgList;
    public MsgAdapter(Context context,List<Msg> msgList){
        this.context = context;
        this.msgList = msgList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.msg_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Msg msg = msgList.get(i);


        if (msg.getType() == Msg.TYPE_RECEIVED){
            // 如果收到消息，显示左边消息布局，将右边消息布局隐藏
            viewHolder.left_layout.setVisibility(View.VISIBLE);
            viewHolder.right_layout.setVisibility(View.GONE);
            viewHolder.left_msg.setText(msg.getContent());
        }else if (msg.getType() == Msg.TYPE_SENT){
            // 如果发出消息，显示右边消息布局，将左边消息布局隐藏
            viewHolder.right_layout.setVisibility(View.VISIBLE);
            viewHolder.left_layout.setVisibility(View.GONE);
            viewHolder.right_msg.setText(msg.getContent());

            Glide.with(context).load(((MyApplication)((Activity)context).getApplication()).getUserInfo().icon).into(viewHolder.civ_icon_right);
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout left_layout,right_layout;
        CircleImageView civ_icon_left,civ_icon_right;
        TextView left_msg,right_msg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            left_layout = itemView.findViewById(R.id.left_layout);
            right_layout = itemView.findViewById(R.id.right_layout);
            civ_icon_left = itemView.findViewById(R.id.civ_icon_left);
            civ_icon_right = itemView.findViewById(R.id.civ_icon_right);
            left_msg = itemView.findViewById(R.id.left_msg);
            right_msg = itemView.findViewById(R.id.right_msg);

        }
    }

}
