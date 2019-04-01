package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;
import com.example.qy.activity.LandmarkAttractionsVideoDetailsActivity;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 30 日 上午 11:13
 * Description:
 */
public class LandmarkAttractionsVideoDetailsAdapter extends RecyclerView.Adapter<LandmarkAttractionsVideoDetailsAdapter.ViewHolder> {
    private Context context;
    public LandmarkAttractionsVideoDetailsAdapter(Context context){
        this.context = context;
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.landmark_attractions_video_details_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
