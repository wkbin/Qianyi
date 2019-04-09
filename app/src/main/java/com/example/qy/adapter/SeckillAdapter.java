package com.example.qy.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.activity.SpecialtyDetailsActivity;
import com.example.qy.bean.Seckill;

import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 18 日 上午 9:10
 * Description: 商城秒杀Adapter
 */
public class SeckillAdapter extends RecyclerView.Adapter<SeckillAdapter.ViewHolder> {
    private List<Seckill> list;
    private Context context;
    public SeckillAdapter(Context context,List<Seckill> list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.seckill_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Seckill seckill = list.get(i);
//        Glide.with(context).load(seckill.imagesPath).into(viewHolder.iv_seckill);
        viewHolder.tv_content.setText(seckill.content);
        viewHolder.tv_price.setText(seckill.price);

        viewHolder.cv_item.setOnClickListener(v -> {
            context.startActivity(new Intent(context,SpecialtyDetailsActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_seckill;
        TextView tv_content;
        TextView tv_price;
        CardView cv_item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_seckill = itemView.findViewById(R.id.iv_seckill);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_price = itemView.findViewById(R.id.tv_price);
            cv_item = itemView.findViewById(R.id.cv_item);
        }
    }
}
