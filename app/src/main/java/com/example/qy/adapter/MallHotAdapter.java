package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;
import com.example.qy.bean.MallHot;
import com.example.qy.fragment.MallFragment;

import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 20 日 上午 10:33
 * Description: 热门推荐Adapter
 */
public class MallHotAdapter extends RecyclerView.Adapter<MallHotAdapter.ViewHolder> {
    private List<MallHot> lists;
    private Context context;
    public MallHotAdapter(Context context,List<MallHot> lists){
        this.context = context;
        this.lists = lists;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mall_hot_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("666","i == "+i);
        MallHot hot = lists.get(i);
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
