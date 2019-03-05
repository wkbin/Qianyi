package com.example.qy.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnImageCapturedListener;
import com.pili.pldroid.player.widget.PLVideoView;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements PLOnImageCapturedListener {
    List<String> playPathList;
    Context context;

    // 点击事件接口
    OnItemClickListener mOnItemClickListener;

    @Override
    public void onImageCaptured(byte[] bytes) {
        Log.d("666","视频截图 == "+bytes.length);
    }

    public interface OnItemClickListener{
        void onClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener ){
        this.mOnItemClickListener=onItemClickListener;
    }

    public HomeAdapter(Context context,List<String> list){
        this.context = context;
        this.playPathList = list;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        PLVideoView PLvv_play;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            PLvv_play = itemView.findViewById(R.id.PLvv_play);
            PLvv_play.setLooping(true);
            AVOptions options = new AVOptions();
            // 设置视频偏好格式MP4
            options.setInteger(AVOptions.KEY_PREFER_FORMAT, 2);
            // 打开视频时单次 http 请求的超时时间，一次打开过程最多尝试五次
            // 单位为 ms
            options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 5 * 1000);

            PLvv_play.setAVOptions(options);
//            View loadingView = itemView.findViewById(R.id.);
//            PLvv_play.setBufferingIndicator(loadingView);
        }

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item,viewGroup,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        String url = playPathList.get(i);
        viewHolder.PLvv_play.setVideoPath(url);
        int aspectRatio = viewHolder.PLvv_play.getDisplayAspectRatio();

        Log.d("HomeAdapter","长宽比 == "+aspectRatio);
//        if (width > height){
//            // 16:9
//            viewHolder.PLvv_play.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_16_9);
//        }else{
//            // 铺满屏幕
//            viewHolder.PLvv_play.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_PAVED_PARENT);
//        }
        if (mOnItemClickListener != null){
            viewHolder.itemView.setOnClickListener(v-> {
                    mOnItemClickListener.onClick(i);
                    Log.d("HomeAdapter","i == "+i);
            });
        }
    }



    @Override
    public int getItemCount() {
        return playPathList.size();
    }
}
