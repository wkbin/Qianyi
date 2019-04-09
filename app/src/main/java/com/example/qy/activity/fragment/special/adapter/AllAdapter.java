package com.example.qy.activity.fragment.special.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 24 日 下午 4:36
 * Description:
 */
public class AllAdapter extends RecyclerView.Adapter<AllAdapter.ViewHolder> {
    private Context context;
    public AllAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.special_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.rc_content.setAdapter(new ContentAdapter(context));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_title;
        TextView tv_name;
        RecyclerView rc_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_name = itemView.findViewById(R.id.tv_name);
            rc_content = itemView.findViewById(R.id.rc_content);
            rc_content.setLayoutManager(new LinearLayoutManager(context));

        }
    }
}
