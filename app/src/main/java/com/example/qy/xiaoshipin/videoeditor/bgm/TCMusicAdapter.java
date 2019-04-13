package com.example.qy.xiaoshipin.videoeditor.bgm;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.qy.R;
import com.example.qy.xiaoshipin.videoeditor.bgm.utils.TCBGMInfo;
import com.example.qy.xiaoshipin.videoeditor.common.widget.BaseRecyclerAdapter;
import com.example.qy.xiaoshipin.videoeditor.common.widget.progress.SampleProgressButton;

import java.util.List;

/**
 * Created by hanszhli on 2017/6/15.
 */

public class TCMusicAdapter extends BaseRecyclerAdapter<TCMusicAdapter.LinearMusicViewHolder> implements View.OnClickListener {
    private static final String TAG = "TCMusicAdapter";
    private Context mContext;
    private List<TCBGMInfo> mBGMList;

    private OnClickSubItemListener mOnClickSubItemListener;

    private SparseArray<LinearMusicViewHolder> mProgressButtonIndexMap = new SparseArray<LinearMusicViewHolder>();

    public void setOnClickSubItemListener(OnClickSubItemListener onClickSubItemListener) {
        mOnClickSubItemListener = onClickSubItemListener;
    }

    public TCMusicAdapter(Context context, List<TCBGMInfo> list) {
        mContext = context;
        mBGMList = list;
    }

    @Override
    public LinearMusicViewHolder onCreateVH(ViewGroup parent, int viewType) {
        LinearMusicViewHolder linearMusicViewHolder = new LinearMusicViewHolder(View.inflate(parent.getContext(), R.layout.item_editer_bgm, null));
        return linearMusicViewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    @Override
    public void onBindVH(LinearMusicViewHolder holder, int position) {
        TCBGMInfo info = mBGMList.get(position);


        holder.btnUse.setMax(100);
        if (info.status == TCBGMInfo.STATE_UNDOWNLOAD) {
            holder.btnUse.setText(mContext.getString(R.string.download));
            holder.btnUse.setState(SampleProgressButton.STATE_NORMAL);
            holder.btnUse.setNormalColor(Color.parseColor("#6C7B8B"));
        } else if (info.status == TCBGMInfo.STATE_DOWNLOADED) {
            holder.btnUse.setText(mContext.getString(R.string.use));
            holder.btnUse.setState(SampleProgressButton.STATE_NORMAL);
            holder.btnUse.setNormalColor(Color.parseColor("#FF6347"));
        } else if (info.status == TCBGMInfo.STATE_DOWNLOADING) {
            holder.btnUse.setText(mContext.getString(R.string.downloading));
            holder.btnUse.setState(SampleProgressButton.STATE_PROGRESS);
            holder.btnUse.setProgress(info.progress);
            holder.btnUse.setNormalColor(Color.parseColor("#FF6347"));
        }
        Log.d(TAG, "onBindVH   info.status:" + info.status);

        holder.tvName.setText(info.name);
        holder.itemView.setTag(position);
        holder.setPosition(position);
        holder.setOnClickSubItemListener(mOnClickSubItemListener);
        holder.setOnItemClickListener(mOnItemClickListener);

        mProgressButtonIndexMap.put(position, holder);
    }

    @Override
    public void onBindViewHolder(LinearMusicViewHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mBGMList.size();
    }

    public void updateItem(int position, TCBGMInfo info) {

        LinearMusicViewHolder holder = mProgressButtonIndexMap.get(position);

        if (holder == null) {
            return;
        }

        if (info.status == TCBGMInfo.STATE_UNDOWNLOAD) {
            holder.btnUse.setText(mContext.getString(R.string.download));
            holder.btnUse.setState(SampleProgressButton.STATE_NORMAL);
            holder.btnUse.setNormalColor(Color.parseColor("#6C7B8B"));
        } else if (info.status == TCBGMInfo.STATE_DOWNLOADED) {
            holder.btnUse.setText(mContext.getString(R.string.use));
            holder.btnUse.setState(SampleProgressButton.STATE_NORMAL);
            holder.btnUse.setNormalColor(Color.parseColor("#FF6347"));
        } else if (info.status == TCBGMInfo.STATE_DOWNLOADING) {
            holder.btnUse.setText(mContext.getString(R.string.downloading));
            holder.btnUse.setState(SampleProgressButton.STATE_PROGRESS);
            holder.btnUse.setProgress(info.progress);
            holder.btnUse.setNormalColor(Color.parseColor("#FF6347"));
        }
        Log.d(TAG, "onBindVH   info.status:" + info.status);

        holder.tvName.setText(info.name);
        holder.itemView.setTag(position);
        holder.setPosition(position);
        holder.setOnClickSubItemListener(mOnClickSubItemListener);
        holder.setOnItemClickListener(mOnItemClickListener);

    }

    public static class LinearMusicViewHolder extends RecyclerView.ViewHolder {
        private SampleProgressButton btnUse;
        private TextView tvName;
        private OnItemClickListener onItemClickListener;
        private int position;

        public LinearMusicViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.bgm_tv_name);
            btnUse = (SampleProgressButton) itemView.findViewById(R.id.btn_use);
            btnUse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickSubItemListener != null) {
                        mOnClickSubItemListener.onClickUseBtn(btnUse, position);
                    }
                }
            });

        }

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

    }

    public interface OnClickSubItemListener {
        void onClickUseBtn(SampleProgressButton button, int position);
    }

}
