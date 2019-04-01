package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 29 日 下午 1:40
 * Description: 关注的评论Adapter
 */
public class AttentionCommentAdapter extends RecyclerView.Adapter<AttentionCommentAdapter.ViewHolder> {
    private Context context;
    public AttentionCommentAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.attention_comment_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
