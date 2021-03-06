package com.example.qy.xiaoshipin.videoeditor.common.widget.videotimeline;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


import com.example.qy.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by vinsonswang on 2017/11/6.
 */

public class VideoProgressView extends FrameLayout {

    private Context mContext;
    private View mRootView;
    private RecyclerView mRecyclerView;
    private int mViewWidth;
    private int mViewHeight;
    private ThumbnailAdapter mThumbnailAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private List<Bitmap> mThumbnailList;

    public VideoProgressView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public VideoProgressView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoProgressView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_video_progress, this);
        mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.rv_video_thumbnail);
        mLinearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    public void setReverse(){
        if(mThumbnailList != null){
            Collections.reverse(mThumbnailList);
            mThumbnailAdapter.notifyDataSetChanged();
        }
    }

    public void setViewWidth(int viewWidth){
        mViewWidth = viewWidth;
    }

    public int getViewWidth(){
        return mViewWidth;
    }

    public int getViewHeight() {
        return mViewHeight;
    }

    public void setViewHeight(int mViewHeight) {
        this.mViewHeight = mViewHeight;
    }

    public void setThumbnailData(List<Bitmap> thumbnailList){
        mThumbnailList = thumbnailList;
        mThumbnailAdapter = new ThumbnailAdapter(mViewWidth, mThumbnailList);
        mRecyclerView.setAdapter(mThumbnailAdapter);
    }

    public RecyclerView getRecyclerView(){
        return mRecyclerView;
    }

    public int getThumbnailCount(){
        return mThumbnailAdapter.getItemCount() - 2;
    }

    public float getSingleThumbnailWidth(){
        return mContext.getResources().getDimension(R.dimen.video_thumbnail_width);
    }

    public ViewGroup getParentView(){
        return (ViewGroup)mRootView;
    }

}
