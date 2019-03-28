package com.example.qy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.activity.SearchHomeActivity;

import java.util.zip.Inflater;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 25 日 下午 4:42
 * Description:
 */
public class HomeSearchAdapter extends RecyclerView.Adapter<HomeSearchAdapter.ViewHolder> {
    private Context context;
    public HomeSearchAdapter(Context context){
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.home_search_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_content.setOnClickListener(v -> {
            context.startActivity(new Intent(context,SearchHomeActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_content;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_content = itemView.findViewById(R.id.tv_content);
        }
    }
}
