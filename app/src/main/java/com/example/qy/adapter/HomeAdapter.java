package com.example.qy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.qy.R;
import com.example.qy.activity.LoginActivity;
import com.example.qy.activity.MusicCollectionActivity;
import com.example.qy.bean.Video;
import com.example.qy.ui.CommentsSheetBottomDialog;
import com.example.qy.ui.VideoShareSheetBottomDialog;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.like.LikeButton;
import com.like.OnLikeListener;
import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLOnImageCapturedListener;
import com.pili.pldroid.player.widget.PLVideoTextureView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.List;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> implements PLOnImageCapturedListener {
    public List<Video> videoList;
    public Context context;
    private FragmentManager manager;
    public int user_id;

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

    public HomeAdapter(Context context,List<Video> list,int user_id,FragmentManager manager){
        this.context = context;
        this.videoList = list;
        this.user_id = user_id;
        this.manager = manager;
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        PLVideoTextureView PLvv_play;
        de.hdodenhof.circleimageview.CircleImageView civ_dp;
        ImageView iv_pause;
        ImageView iv_yficon;
        ImageView iv_yficon1;
        TextView tv_like_count;
        TextView tv_comments_count;
        LikeButton lb_like;
        ImageView iv_comments;
        RelativeLayout rl_dp;

        LinearLayout li_video_share;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_like_count = itemView.findViewById(R.id.tv_like_count);
            tv_comments_count = itemView.findViewById(R.id.tv_comments_count);
            lb_like = itemView.findViewById(R.id.lb_like);
            iv_comments = itemView.findViewById(R.id.iv_comments);
            rl_dp = itemView.findViewById(R.id.rl_dp);

            civ_dp = itemView.findViewById(R.id.civ_dp);
            iv_pause = itemView.findViewById(R.id.iv_pause);
            iv_yficon = itemView.findViewById(R.id.iv_yficon);
            iv_yficon1 = itemView.findViewById(R.id.iv_yficon1);
            PLvv_play = itemView.findViewById(R.id.PLvv_play);
            li_video_share = itemView.findViewById(R.id.li_video_share);

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

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        Video video = videoList.get(i);
        String url = video.videoPath;
        viewHolder.PLvv_play.setVideoPath(url);
        viewHolder.tv_like_count.setText(video.countLike+"");
        viewHolder.tv_comments_count.setText(video.countComments);
        viewHolder.li_video_share.setOnClickListener(v->{
            VideoShareSheetBottomDialog dialog = new VideoShareSheetBottomDialog(context);
            dialog.show();
        });

        viewHolder.rl_dp.setOnClickListener(v->{
            context.startActivity(new Intent(context,MusicCollectionActivity.class));
        });

        viewHolder.lb_like.setLiked(video.liked);

        viewHolder.lb_like.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                if (user_id == 0){
                    context.startActivity(new Intent(context,LoginActivity.class));
                    return;
                }
                HttpUtils.sendOkHttpRequest(HttpQYUtils.getAddLike(user_id, video.id), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ((Activity)context).runOnUiThread(()->{
                            ToastUtils.showShort(context,"网络连接失败");
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText =response.body().string();
                        try {
                            JSONObject object = new JSONObject(responseText);
                            if (object.getBoolean("isSuc")){
                                ((Activity)context).runOnUiThread(()->{
                                    video.countLike += 1;
                                    viewHolder.tv_like_count.setText(video.countLike+"");
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                if (user_id == 0){
                    context.startActivity(new Intent(context,LoginActivity.class));
                    return;
                }
                HttpUtils.sendOkHttpRequest(HttpQYUtils.getDelLike(user_id, video.id), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ((Activity)context).runOnUiThread(()->{
                            ToastUtils.showShort(context,"网络连接失败");
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText =response.body().string();
                        try {
                            JSONObject object = new JSONObject(responseText);
                            if (object.getBoolean("isSuc")){
                                ((Activity)context).runOnUiThread(()->{
                                    video.countLike -= 1;
                                    viewHolder.tv_like_count.setText(video.countLike+"");
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        viewHolder.iv_comments.setOnClickListener(v->{

            CommentsSheetBottomDialog dialog = new CommentsSheetBottomDialog();
            Bundle bundle = new Bundle();
            bundle.putInt("user_id",user_id);
            bundle.putString("video_id",video.id);
            dialog.setArguments(bundle);
            dialog.show(manager,"tag");
        });


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
        return videoList.size();
    }
}
