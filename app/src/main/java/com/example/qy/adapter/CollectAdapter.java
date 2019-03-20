package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.bean.Collect;
import com.example.qy.ui.MyVideoShareSheetBottomDialog;
import com.example.qy.ui.RoundImageView;
import com.example.qy.utils.ToastUtils;

import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 09 日 下午 1:51
 * Description: 收藏Adapter
 */
public class CollectAdapter extends RecyclerView.Adapter<CollectAdapter.ViewHolder> {
    private List<Collect> list;
    private Context context;
    public CollectAdapter(Context context,List<Collect> list){
        this.context = context;
        this.list = list;
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        RoundImageView iv_collect_image;
        TextView tv_collect_title;
        TextView tv_collect_like;
//        TextView tv_collect_details;
        ImageView iv_more;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_collect_image = itemView.findViewById(R.id.iv_collect_image);
            tv_collect_title = itemView.findViewById(R.id.tv_collect_title);
            tv_collect_like = itemView.findViewById(R.id.tv_collect_like);
//            tv_collect_details = itemView.findViewById(R.id.tv_collect_details);
            iv_more = itemView.findViewById(R.id.iv_more);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.collect_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Collect collect = list.get(i);
        Glide.with(context).load(collect.imageUrl).into(viewHolder.iv_collect_image);
        viewHolder.tv_collect_title.setText(collect.title);
        viewHolder.tv_collect_like.setText(collect.like);
        Log.d("CollectAdapter","打印 == "+collect.toString());
//        viewHolder.tv_collect_details.setOnClickListener(v-> {
//                ToastUtils.showShort(context,i+"");
//        });
        viewHolder.iv_more.setOnClickListener(v->{
            MyVideoShareSheetBottomDialog dialog = new MyVideoShareSheetBottomDialog(context);
            dialog.show();
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
