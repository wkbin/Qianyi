package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qy.R;

import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 21 日 下午 4:02
 * Description: 表情Adapter
 */
public class FaceAdapter extends RecyclerView.Adapter<FaceAdapter.ViewHolder> {
    private Context context;
    private List<Integer> list;
    public FaceAdapter(Context context,List list){
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.face_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        int id = list.get(i);
        viewHolder.iv_face.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_face;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_face = itemView.findViewById(R.id.iv_face);
        }
    }
}
