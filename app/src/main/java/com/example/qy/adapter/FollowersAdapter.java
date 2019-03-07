package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.bean.Follwers;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowersAdapter extends RecyclerView.Adapter<FollowersAdapter.ViewHolder> {
    public List<Follwers> lists;
    public Context context;
    public FollowersAdapter(Context context, List<Follwers> lists){
        this.lists = lists;
        this.context = context;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civ_followers_icon;
        TextView tv_followers_name;
        TextView tv_followers_work_and_fans;
        Button btn_followers_focus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civ_followers_icon = itemView.findViewById(R.id.civ_followers_icon);
            tv_followers_name = itemView.findViewById(R.id.tv_followers_name);
            tv_followers_work_and_fans = itemView.findViewById(R.id.tv_followers_work_and_fans);
            btn_followers_focus = itemView.findViewById(R.id.btn_followers_focus);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.followers_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Follwers follwers = lists.get(i);
        Glide.with(context).load(follwers.icon).into(viewHolder.civ_followers_icon);
        viewHolder.tv_followers_name.setText(follwers.name);
        viewHolder.tv_followers_work_and_fans.setText(follwers.signature);
        if (follwers.together.equals("0")){
            viewHolder.btn_followers_focus.setText("已关注");
        }else{
            viewHolder.btn_followers_focus.setText("互相关注");
        }

    }


    @Override
    public int getItemCount() {
        return lists.size();
    }
}
