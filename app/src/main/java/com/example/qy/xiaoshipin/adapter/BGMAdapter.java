package com.example.qy.xiaoshipin.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.utils.ToastUtils;
import com.example.qy.xiaoshipin.bean.Music;
import com.example.qy.xiaoshipin.videoeditor.common.widget.BaseRecyclerAdapter;
import com.example.qy.xiaoshipin.videoeditor.common.widget.progress.SampleProgressButton;
import com.tencent.ugc.TXUGCRecord;
import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 11 日 下午 6:32
 * Description: BGM
 */
public class BGMAdapter extends BaseRecyclerAdapter<BGMAdapter.LinearMusicViewHolder>{
    private Context mContext;
    private List<Music> mMusicList;

    private OnClickSubItemListener mOnClickSubItemListener;
    private SparseArray<LinearMusicViewHolder> mProgressButtonIndexMap = new SparseArray<LinearMusicViewHolder>();

    public void setOnClickSubItemListener(OnClickSubItemListener onClickSubItemListener) {
        mOnClickSubItemListener = onClickSubItemListener;
    }


    @Override
    public void onBindVH(LinearMusicViewHolder holder, int position) {
        Music music = mMusicList.get(position);
        Glide.with(mContext).load(music.musicImage).into(holder.iv_music_image);
        holder.tv_music_name.setText(music.musicName);
        holder.tv_creator_name.setText(music.creatorName);

        holder.iv_music_play.setOnClickListener(v->{
            ToastUtils.showShort(mContext,"播放"+music.musicName);
            TXUGCRecord mTXCameraRecord = TXUGCRecord.getInstance(mContext.getApplicationContext());
            mTXCameraRecord.setBGM(music.musicPath);
            mTXCameraRecord.playBGMFromTime(0,600000);
        });

        holder.btn_use.setMax(100);
        if (music.status == Music.STATE_UNDOWNLOAD) {
            holder.btn_use.setText(mContext.getString(R.string.download));
            holder.btn_use.setState(SampleProgressButton.STATE_NORMAL);
            holder.btn_use.setNormalColor(Color.parseColor("#6C7B8B"));
        } else if (music.status == Music.STATE_DOWNLOADED) {
            holder.btn_use.setText(mContext.getString(R.string.use));
            holder.btn_use.setState(SampleProgressButton.STATE_NORMAL);
            holder.btn_use.setNormalColor(Color.parseColor("#FF6347"));
        } else if (music.status == Music.STATE_DOWNLOADING) {
            holder.btn_use.setText(mContext.getString(R.string.downloading));
            holder.btn_use.setState(SampleProgressButton.STATE_PROGRESS);
            holder.btn_use.setProgress(music.progress);
            holder.btn_use.setNormalColor(Color.parseColor("#FF6347"));
        }

        holder.itemView.setTag(position);
        holder.setPosition(position);
        holder.setOnClickSubItemListener(mOnClickSubItemListener);
        holder.setOnItemClickListener(mOnItemClickListener);

        mProgressButtonIndexMap.put(position, holder);
    }

    @Override
    public LinearMusicViewHolder onCreateVH(ViewGroup parent, int viewType) {

        return  new LinearMusicViewHolder(LayoutInflater.from(mContext).inflate(R.layout.bgm_item,parent,false));
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }




    public static class LinearMusicViewHolder extends RecyclerView.ViewHolder {
        private SampleProgressButton btn_use;
        private OnItemClickListener onItemClickListener;
        private int position;
        private ImageView iv_music_play;
        private ImageView iv_music_image;
        private TextView tv_music_name;
        private TextView tv_creator_name;


        public void setPosition(int position) {
            this.position = position;
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
        private OnClickSubItemListener mOnClickSubItemListener;
        public void setOnClickSubItemListener(OnClickSubItemListener onClickSubItemListener) {
            mOnClickSubItemListener = onClickSubItemListener;
        }

        public LinearMusicViewHolder(View itemView) {
            super(itemView);
            iv_music_play = itemView.findViewById(R.id.iv_music_play);
            iv_music_image = itemView.findViewById(R.id.iv_music_image);
            tv_music_name = itemView.findViewById(R.id.tv_music_name);
            tv_creator_name = itemView.findViewById(R.id.tv_creator_name);

            btn_use = (SampleProgressButton) itemView.findViewById(R.id.btn_use);
            btn_use.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickSubItemListener != null) {
                        mOnClickSubItemListener.onClickUseBtn(btn_use, position);
                    }
                }
            });

        }



    }


    public BGMAdapter(Context context,List<Music> musicList){
        this.mContext = context;
        this.mMusicList = musicList;
    }

    @Override
    public void onBindViewHolder(LinearMusicViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    public void updateItem(int position, Music music) {

        LinearMusicViewHolder holder = mProgressButtonIndexMap.get(position);

        if (holder == null) {
            return;
        }

        if (music.status == Music.STATE_UNDOWNLOAD) {
            holder.btn_use.setText(mContext.getString(R.string.download));
            holder.btn_use.setState(SampleProgressButton.STATE_NORMAL);
            holder.btn_use.setNormalColor(Color.parseColor("#6C7B8B"));
        } else if (music.status == Music.STATE_DOWNLOADED) {
            holder.btn_use.setText(mContext.getString(R.string.use));
            holder.btn_use.setState(SampleProgressButton.STATE_NORMAL);
            holder.btn_use.setNormalColor(Color.parseColor("#FF6347"));
        } else if (music.status == Music.STATE_DOWNLOADING) {
            holder.btn_use.setText(mContext.getString(R.string.downloading));
            holder.btn_use.setState(SampleProgressButton.STATE_PROGRESS);
            holder.btn_use.setProgress(music.progress);
            holder.btn_use.setNormalColor(Color.parseColor("#FF6347"));
        }

        Glide.with(mContext).load(music.musicImage).into(holder.iv_music_image);
        holder.tv_music_name.setText(music.musicName);
        holder.tv_creator_name.setText(music.creatorName);

        holder.iv_music_play.setOnClickListener(v->{
            ToastUtils.showShort(mContext,"播放"+music.musicName);
            TXUGCRecord mTXCameraRecord = TXUGCRecord.getInstance(mContext.getApplicationContext());
            mTXCameraRecord.setBGM(music.musicPath);
            mTXCameraRecord.playBGMFromTime(0,600000);
        });




        holder.itemView.setTag(position);
        holder.setPosition(position);
        holder.setOnClickSubItemListener(mOnClickSubItemListener);
        holder.setOnItemClickListener(mOnItemClickListener);


    }


    public interface OnClickSubItemListener {
        void onClickUseBtn(SampleProgressButton button, int position);
    }

}
