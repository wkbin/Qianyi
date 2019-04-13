package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.ui.ChooseDestinationDialog;

import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 09 日 下午 3:47
 * Description:
 */
public class ChooseDestinationAdapter extends RecyclerView.Adapter<ChooseDestinationAdapter.ViewHolder> {
    private List<String> nameList;
    private Context context;
    public ChooseDestinationAdapter(Context context,List<String> nameList){
        this.context = context;
        this.nameList = nameList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.destination_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String name = nameList.get(i);
        viewHolder.tv_name.setText(name);
    }

    @Override
    public int getItemCount() {
        return nameList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_dingwei;
        TextView tv_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_dingwei = itemView.findViewById(R.id.iv_dingwei);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
