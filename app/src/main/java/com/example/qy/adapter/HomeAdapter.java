package com.example.qy.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.MediaController;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.IMediaController;
import com.pili.pldroid.player.PLOnImageCapturedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import com.pili.pldroid.player.widget.PLVideoView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements PLOnImageCapturedListener {
    List<String> playPathList;
    public Context context;

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
        PLVideoTextureView PLvv_play;
        CircleImageView civ_dp;
        ImageView iv_pause;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civ_dp = itemView.findViewById(R.id.civ_dp);
            iv_pause = itemView.findViewById(R.id.iv_pause);





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

            PLvv_play.setDisplayAspectRatio(PLVideoTextureView.ASPECT_RATIO_PAVED_PARENT);
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





        if (mOnItemClickListener != null){
            viewHolder.itemView.setOnClickListener(v-> {
                    mOnItemClickListener.onClick(i);
                    Log.d("HomeAdapter","i == "+i);
            });
        }
    }


    // 获取视频第一帧缩略图
    public static Bitmap getVideoThumbnail(String videoPath) {
        MediaMetadataRetriever media =new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        Bitmap bitmap = media.getFrameAtTime();
        return bitmap;
    }

    @Override
    public int getItemCount() {
        return playPathList.size();
    }
}
