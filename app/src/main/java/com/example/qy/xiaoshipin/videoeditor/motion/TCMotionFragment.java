package com.example.qy.xiaoshipin.videoeditor.motion;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.tencent.liteav.basic.log.TXCLog;

import com.example.qy.xiaoshipin.videoeditor.BaseEditFragment;
import com.example.qy.xiaoshipin.videoeditor.TCVideoEditerWrapper;
import com.example.qy.xiaoshipin.videoeditor.TCVideoEffectActivity;
import com.example.qy.xiaoshipin.videoeditor.common.widget.videotimeline.ColorfulProgress;
import com.example.qy.xiaoshipin.videoeditor.common.widget.videotimeline.VideoProgressController;
import com.tencent.ugc.TXVideoEditConstants;
import com.tencent.ugc.TXVideoEditer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hans on 2017/11/7.
 * <p>
 * 动态滤镜特效的设置Fragment
 */
public class TCMotionFragment extends BaseEditFragment implements View.OnClickListener, View.OnTouchListener {
    private static final String TAG = "TCMotionFragment";

    private boolean mIsOnTouch; // 是否已经有按下的
    private TXVideoEditer mTXVideoEditer;

    private long mVideoDuration;
    private ColorfulProgress mColorfulProgress;
    private VideoProgressController mActivityVideoProgressController;
    private ImageView mIvDelete;
    private boolean mStartMark;


    private Map<Integer, TCMotionItem> mMotionMap;

    private static class TCMotionItem {
        public int ivID;
        public int rlSelectID;
        public int animID;
        public int effectID;

        TCMotionItem(int ivID, int rlSelectID, int animID, int effectID) {
            this.ivID = ivID;
            this.rlSelectID = rlSelectID;
            this.animID = animID;
            this.effectID = effectID;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_motion, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TCVideoEditerWrapper wrapper = TCVideoEditerWrapper.getInstance();
        mTXVideoEditer = wrapper.getEditer();
        if (mTXVideoEditer != null) {
            mVideoDuration = wrapper.getTXVideoInfo().duration;
        }
        mActivityVideoProgressController = ((TCVideoEffectActivity) getActivity()).getVideoProgressViewController();
        initViews(view);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (mColorfulProgress != null) {
            mColorfulProgress.setVisibility(hidden ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        List<ColorfulProgress.MarkInfo> markInfoList = mColorfulProgress.getMarkInfoList();
        TCMotionViewInfoManager.getInstance().setMarkInfoList(markInfoList);
    }

    private void initViews(View view) {
        mMotionMap = new HashMap<>();
        mMotionMap.put(R.id.btn_soul_out, new TCMotionItem(R.id.btn_soul_out, R.id.rl_spirit_out_select_container, R.drawable.motion_soul_out, TXVideoEditConstants.TXEffectType_SOUL_OUT));
        mMotionMap.put(R.id.btn_split, new TCMotionItem(R.id.btn_split, R.id.rl_split_select_container, R.drawable.motion_split_screen, TXVideoEditConstants.TXEffectType_SPLIT_SCREEN));
        mMotionMap.put(R.id.btn_rock_light, new TCMotionItem(R.id.btn_rock_light, R.id.rl_light_wave_select_container, R.drawable.motion_rock_light, TXVideoEditConstants.TXEffectType_ROCK_LIGHT));
        mMotionMap.put(R.id.btn_dark_dream, new TCMotionItem(R.id.btn_dark_dream, R.id.rl_dark_select_container, R.drawable.motion_dark_dream, TXVideoEditConstants.TXEffectType_DARK_DRAEM));
        mMotionMap.put(R.id.btn_win_shadow, new TCMotionItem(R.id.btn_win_shadow, R.id.rl_win_shadow_select_container, R.drawable.motion_win_shaddow, TXVideoEditConstants.TXEffectType_WIN_SHADDOW));
        mMotionMap.put(R.id.btn_ghost_shadow, new TCMotionItem(R.id.btn_ghost_shadow, R.id.rl_ghost_shadow_select_container, R.drawable.motion_ghost_shaddow, TXVideoEditConstants.TXEffectType_GHOST_SHADDOW));
        mMotionMap.put(R.id.btn_phantom_shadow, new TCMotionItem(R.id.btn_phantom_shadow, R.id.rl_phantom_shadow_select_container, R.drawable.motion_phantom_shaddow, TXVideoEditConstants.TXEffectType_PHANTOM_SHADDOW));
        mMotionMap.put(R.id.btn_ghost, new TCMotionItem(R.id.btn_ghost, R.id.rl_ghost_select_container, R.drawable.motion_ghost, TXVideoEditConstants.TXEffectType_GHOST));
        mMotionMap.put(R.id.btn_lightning, new TCMotionItem(R.id.btn_lightning, R.id.rl_lightning_select_container, R.drawable.motion_lightning, TXVideoEditConstants.TXEffectType_LIGHTNING));
        mMotionMap.put(R.id.btn_mirror, new TCMotionItem(R.id.btn_mirror, R.id.rl_mirror_select_container, R.drawable.motion_mirror, TXVideoEditConstants.TXEffectType_MIRROR));
        mMotionMap.put(R.id.btn_illusion, new TCMotionItem(R.id.btn_illusion, R.id.rl_illusion_select_container, R.drawable.motion_illusion, TXVideoEditConstants.TXEffectType_ILLUSION));

        for (Map.Entry<Integer, TCMotionItem> entry : mMotionMap.entrySet()) {
            TCMotionItem item = entry.getValue();
            ImageButton ibtn = (ImageButton) view.findViewById(item.ivID);
            ibtn.setOnTouchListener(this);
            Glide.with(this).load(item.animID).into(ibtn);
        }

        mIvDelete = (ImageView) view.findViewById(R.id.iv_undo);
        mIvDelete.setOnClickListener(this);

        mColorfulProgress = new ColorfulProgress(getContext());
        mColorfulProgress.setWidthHeight(mActivityVideoProgressController.getThumbnailPicListDisplayWidth(), getResources().getDimensionPixelOffset(R.dimen.video_progress_height));
        mColorfulProgress.setMarkInfoList(TCMotionViewInfoManager.getInstance().getMarkInfoList());
        mActivityVideoProgressController.addColorfulProgress(mColorfulProgress);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_undo:
                deleteLastMotion();
                break;
        }
    }

    private void deleteLastMotion() {
        ColorfulProgress.MarkInfo markInfo = mColorfulProgress.deleteLastMark();
        if (markInfo != null) {
            mActivityVideoProgressController.setCurrentTimeMs(markInfo.startTimeMs);
            TCVideoEffectActivity parentActivity = (TCVideoEffectActivity) getActivity();
            parentActivity.previewAtTime(markInfo.startTimeMs);
        }

        mTXVideoEditer.deleteLastEffect();
        if (mColorfulProgress.getMarkListSize() > 0) {
            showDeleteBtn();
        } else {
            hideDeleteBtn();
        }
    }

    public void showDeleteBtn() {
        if (mColorfulProgress.getMarkListSize() > 0) {
            mIvDelete.setVisibility(View.VISIBLE);
        }
    }

    public void hideDeleteBtn() {
        if (mColorfulProgress.getMarkListSize() == 0) {
            mIvDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (mIsOnTouch && action == MotionEvent.ACTION_DOWN) {
            return false;
        }

        TCMotionItem item = mMotionMap.get(view.getId());
        if (item != null) {
            RelativeLayout rlSelect = (RelativeLayout) getActivity().findViewById(item.rlSelectID);
            if (action == MotionEvent.ACTION_DOWN) {
                rlSelect.setVisibility(View.VISIBLE);
                pressMotion(item.effectID);
                mIsOnTouch = true;
            }
            if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
                rlSelect.setVisibility(View.INVISIBLE);
                upMotion(item.effectID);
                mIsOnTouch = false;
            }
            return false;
        }

        return false;
    }

    private void pressMotion(int type) {
        // 未开始播放 则开始播放
        long currentTime = mActivityVideoProgressController.getCurrentTimeMs();

        if (((TCVideoEffectActivity) getActivity()).isPreviewFinish) {
            TXCLog.i(TAG, "pressMotion, preview finished, ignore");
            mStartMark = false;
            return;
        }
        mStartMark = true;
        ((TCVideoEffectActivity) getActivity()).playVideo(true);
        mTXVideoEditer.startEffect(type, currentTime);

        switch (type) {
            case TXVideoEditConstants.TXEffectType_SOUL_OUT:
                // 进度条开始变颜色
                mColorfulProgress.startMark(getResources().getColor(R.color.spirit_out_color_press));
                break;
            case TXVideoEditConstants.TXEffectType_SPLIT_SCREEN:
                mColorfulProgress.startMark(getResources().getColor(R.color.screen_split_press));
                break;
            case TXVideoEditConstants.TXEffectType_ROCK_LIGHT:
                mColorfulProgress.startMark(getResources().getColor(R.color.light_wave_press));
                break;
            case TXVideoEditConstants.TXEffectType_DARK_DRAEM:
                mColorfulProgress.startMark(getResources().getColor(R.color.dark_illusion_press));
                break;
            case TXVideoEditConstants.TXEffectType_WIN_SHADDOW:
                mColorfulProgress.startMark(getResources().getColor(R.color.win_shaddow_color_press));
                break;
            case TXVideoEditConstants.TXEffectType_GHOST_SHADDOW:
                mColorfulProgress.startMark(getResources().getColor(R.color.ghost_shaddow_color_press));
                break;
            case TXVideoEditConstants.TXEffectType_PHANTOM_SHADDOW:
                mColorfulProgress.startMark(getResources().getColor(R.color.phantom_shaddow_color_press));
                break;
            case TXVideoEditConstants.TXEffectType_GHOST:
                mColorfulProgress.startMark(getResources().getColor(R.color.ghost_color_press));
                break;
            case TXVideoEditConstants.TXEffectType_LIGHTNING:
                mColorfulProgress.startMark(getResources().getColor(R.color.lightning_color_press));
                break;
            case TXVideoEditConstants.TXEffectType_MIRROR:
                mColorfulProgress.startMark(getResources().getColor(R.color.mirror_color_press));
                break;
            case TXVideoEditConstants.TXEffectType_ILLUSION:
                mColorfulProgress.startMark(getResources().getColor(R.color.illusion_color_press));
                break;
        }
    }

    private void upMotion(int type) {
        if (!mStartMark) {
            return;
        }
        // 暂停播放
        ((TCVideoEffectActivity) getActivity()).pausePlay();
        // 进度条结束标记
        mColorfulProgress.endMark();

        // 特效结束时间
        long currentTime = mActivityVideoProgressController.getCurrentTimeMs();
        mTXVideoEditer.stopEffect(type, currentTime);
        // 显示撤销的按钮
        showDeleteBtn();
    }
}
