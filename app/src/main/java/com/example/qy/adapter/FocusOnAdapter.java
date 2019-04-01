package com.example.qy.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 29 日 下午 1:08
 * Description:
 */
public class FocusOnAdapter extends RecyclerView.Adapter<FocusOnAdapter.ViewHolder> {
    private Context context;
    public FocusOnAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.focus_on_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.rc_comments.setAdapter(new AttentionCommentAdapter(context));

        viewHolder.iv_more_2.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setMessage("是否取消关注？");
            dialog.setPositiveButton("确定", (d,which)->{

            });
            dialog.setNegativeButton("取消",(d,which)->{

            });
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerView rc_comments;
        ImageView iv_more_2;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rc_comments = itemView.findViewById(R.id.rc_comments);
            iv_more_2 = itemView.findViewById(R.id.iv_more_2);
            rc_comments.setLayoutManager(new LinearLayoutManager(context));
        }
    }
}
