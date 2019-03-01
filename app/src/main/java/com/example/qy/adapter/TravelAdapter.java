package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.bean.Travel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TravelAdapter extends RecyclerView.Adapter<TravelAdapter.ViewHolder> {
    private Context context;
    private List<Travel> lists;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_travel_image;
        TextView tv_travel_text;
        CircleImageView civ_travel_icon;
        TextView tv_travel_user_name;
        TextView tv_travel_number;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_travel_image = itemView.findViewById(R.id.iv_travel_image);
            tv_travel_text = itemView.findViewById(R.id.tv_travel_text);
            civ_travel_icon = itemView.findViewById(R.id.civ_travel_icon);
            tv_travel_user_name = itemView.findViewById(R.id.tv_travel_user_name);
            tv_travel_number = itemView.findViewById(R.id.tv_travel_number);
        }
    }

    public TravelAdapter(Context context,List<Travel> list){
        this.context = context;
        this.lists = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.travel_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Travel travel = lists.get(i);
        Glide.with(context).load(travel.getImagePath()).into(viewHolder.iv_travel_image);
        Glide.with(context).load(travel.getIconPath()).into(viewHolder.civ_travel_icon);
        viewHolder.tv_travel_text.setText(travel.getTravelText());
        viewHolder.tv_travel_user_name.setText(travel.getUserName());
        viewHolder.tv_travel_number.setText(travel.getTravelNumber());
    }
    @Override
    public int getItemCount() {
        return lists.size();
    }

}
