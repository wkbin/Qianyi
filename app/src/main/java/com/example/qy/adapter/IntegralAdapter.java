package com.example.qy.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.bean.Integral;

import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 08 日 下午 1:59
 * Description:
 */
public class IntegralAdapter extends RecyclerView.Adapter<IntegralAdapter.ViewHolder>{
    private List<Integral> list;
    private Context context;

    public IntegralAdapter(Context context,List<Integral> list){
        this.context = context;
        this.list = list;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_type;
        public TextView tv_date;
        public TextView tv_growth;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_type = itemView.findViewById(R.id.tv_type);
            tv_date = itemView.findViewById(R.id.tv_date);
            tv_growth = itemView.findViewById(R.id.tv_growth);
        }
    }
    @NonNull
    @Override
    public IntegralAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.integral_subsidiary_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull IntegralAdapter.ViewHolder viewHolder, int i) {
        Integral integral = list.get(i);
        viewHolder.tv_growth.setTextColor(Color.parseColor("#3DB2A3"));
        switch (integral.integralType){
            case 0:
                viewHolder.tv_growth.setTextColor(Color.parseColor("#1A1A1A"));
                viewHolder.tv_type.setText("积分商城兑换物品");
                break;
            case 1:
                viewHolder.tv_type.setText("每日连续签到");
                break;
            case 2:
                viewHolder.tv_type.setText("看视频");
                break;
            case 3:
                viewHolder.tv_type.setText("看商城购买");
                break;
            case 4:
                viewHolder.tv_type.setText("看分享给好友");
                break;
            case 5:
                viewHolder.tv_type.setText("绑定手机");
                break;
        }
        viewHolder.tv_growth.setText(""+integral.integralNumber);
        viewHolder.tv_date.setText(integral.integralTime);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
