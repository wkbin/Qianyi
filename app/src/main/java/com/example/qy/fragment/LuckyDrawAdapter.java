package com.example.qy.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.example.qy.R;
import com.example.qy.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 29 日 下午 8:40
 * Description: 抽奖Adapter
 */
public class LuckyDrawAdapter extends RecyclerView.Adapter<LuckyDrawAdapter.ViewHolder> {
    private Context context;
    private int[] images;
    private int[] id = {0,1,2,5,8,7,6,4};

//    public android.os.Handler handler = new android.os.Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 0x222){
//
//            }
//        }
//    };
    interface OnClickListener{
        void onClick(View v);
    }
    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public LuckyDrawAdapter(Context context, int[] images){
        this.context = context;
        this.images = images;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.lucky_draw_item,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.iv_images.setImageResource(images[i]);
        if (i == 4){
            viewHolder.rl_cj.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv_images;
        public RelativeLayout rl_cj;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_images = itemView.findViewById(R.id.iv_images);
            rl_cj = itemView.findViewById(R.id.rl_cj);
        }
    }
}
