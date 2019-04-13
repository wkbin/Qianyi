package com.example.qy.xiaoshipin.videoeditor.bgm;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;
import com.tencent.liteav.basic.log.TXCLog;

import com.example.qy.xiaoshipin.common.utils.TCConstants;
import com.example.qy.xiaoshipin.videoeditor.BaseEditFragment;
import com.example.qy.xiaoshipin.videoeditor.TCVideoEditerWrapper;
import com.example.qy.xiaoshipin.videoeditor.bgm.view.TCBGMPannel;
import com.example.qy.xiaoshipin.videoeditor.utils.DialogUtil;
import com.example.qy.xiaoshipin.videoeditor.utils.DraftEditer;
import com.example.qy.xiaoshipin.videoeditor.utils.EffectEditer;
import com.tencent.ugc.TXVideoEditer;

import java.io.IOException;

/**
 * RangeSlider
 * Created by hans on 2017/11/6.
 * <p>
 * bgm设置的fragment
 */
public class TCBGMSettingFragment extends BaseEditFragment {
    private static final String TAG = "TCBGMSettingFragment";

    /**
     * 控制面板相关
     */
    private int mBgmPosition = -1;
    private TCBGMPannel mTCBGMPannel;
    private String mBGMPath;
    private int mBgmDuration;
    private DraftEditer mDraftEditer;
    private EffectEditer mEffectEditer;

    private String mBGMName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDraftEditer = DraftEditer.getInstance();
        mEffectEditer = EffectEditer.getInstance();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String path = mDraftEditer.getBgmPath();
        if (!TextUtils.isEmpty(path)) {
            mBGMPath = path;
        } else {
            chooseBGM();
            return;
        }
        String name = mDraftEditer.getBgmName();
        if (!TextUtils.isEmpty(name)) {
            mTCBGMPannel.setMusicName(name);
        }
        mBgmPosition = mDraftEditer.getBgmPos();

        float vidoVolume = mDraftEditer.getVideoVolume();
        if (vidoVolume != -1) {
            mTCBGMPannel.setVideoVolume(vidoVolume);
        }
        float bgmVolume = mDraftEditer.getBgmVolume();
        if (bgmVolume != -1) {
            mTCBGMPannel.setBgmVolume(bgmVolume);
        }

        long bgmDuration = mDraftEditer.getBgmDuration();
        if (bgmDuration != 0) {
            mTCBGMPannel.setBgmDuration(bgmDuration);
        }
        long startTime = mDraftEditer.getBgmStartTime();
        long endTime = mDraftEditer.getBgmEndTime();
        if (startTime != -1 && endTime != -1) {
            mTCBGMPannel.setCutRange(startTime, endTime);
        }
    }

    private void chooseBGM() {
        Intent bgmIntent = new Intent(getActivity(), BGMSelectActivity.class);
        bgmIntent.putExtra(TCConstants.BGM_POSITION, mBgmPosition);
        startActivityForResult(bgmIntent, TCConstants.ACTIVITY_BGM_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            getActivity().finish();
            return;
        }
        mBGMPath = data.getStringExtra(TCConstants.BGM_PATH);
        mBGMName = data.getStringExtra(TCConstants.BGM_NAME);
        mBgmPosition = data.getIntExtra(TCConstants.BGM_POSITION, -1);
        mTCBGMPannel.setMusicName(mBGMName);
        if (TextUtils.isEmpty(mBGMPath)) {
            getActivity().finish();
            return;
        }
        TXVideoEditer editer = TCVideoEditerWrapper.getInstance().getEditer();
        int result = editer.setBGM(mBGMPath);
        if (result != 0) {
            DialogUtil.showDialog(getContext(),
                    getResources().getString(R.string.tc_bgm_setting_fragment_video_edit_failed),
                    getResources().getString(R.string.tc_bgm_setting_fragment_background_sound_only_supports_mp3_or_m4a_format),
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
        }
        try {
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(mBGMPath);
            mediaPlayer.prepare();
            mBgmDuration = mediaPlayer.getDuration();
            TXCLog.i(TAG, "onActivityResult, BgmDuration = " + mBgmDuration);
            mediaPlayer.release();
        } catch (IOException e) {
            e.printStackTrace();
        }
        editer.setBGMStartTime(0, mBgmDuration);
        editer.setBGMVolume(0.5f);
        editer.setVideoVolume(0.5f);
        if (mTCBGMPannel != null) {
            mTCBGMPannel.setVideoVolume(0.5f);
            mTCBGMPannel.setBgmVolume(0.5f);
            mTCBGMPannel.setBgmDuration(mBgmDuration);
        }
        //保存配置
        mDraftEditer.setBgmName(mBGMName);
        mDraftEditer.setBgmPath(mBGMPath);
        mDraftEditer.setBgmStartTime(0);
        mDraftEditer.setBgmEndTime(mBgmDuration);
        mDraftEditer.setBgmPos(mBgmPosition);
        mDraftEditer.setBgmVolume(0.5f);
        mDraftEditer.setVideoVolume(0.5f);
        mDraftEditer.setBgmDuration(mBgmDuration);

        mTCBGMPannel.setBgmDuration(mBgmDuration);
        mTCBGMPannel.setVideoVolume(0.5f);
        mTCBGMPannel.setBgmVolume(0.5f);
        mTCBGMPannel.setCutRange(0, mBgmDuration);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bgm, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMusicPanel(view);
    }

    /**
     * bgm 播放时间区间设置
     */
    private void onSetBGMStartTime(long startTime, long endTime) {
        TXVideoEditer editer = TCVideoEditerWrapper.getInstance().getEditer();
        editer.setBGMStartTime(startTime, endTime);
    }

    /**
     * ==============================================音乐列表相关==============================================
     */
    private void initMusicPanel(View view) {
        mTCBGMPannel = (TCBGMPannel) view.findViewById(R.id.tc_record_bgm_pannel);
        mTCBGMPannel.hideOkButton();
        mTCBGMPannel.setOnBGMChangeListener(new TCBGMPannel.BGMChangeListener() {
            @Override
            public void onMicVolumeChanged(float volume) {
                mDraftEditer.setVideoVolume(volume);

                TXVideoEditer editer = TCVideoEditerWrapper.getInstance().getEditer();
                editer.setVideoVolume(volume);
            }

            @Override
            public void onBGMVolumChanged(float volume) {
                mDraftEditer.setBgmVolume(volume);

                TXVideoEditer editer = TCVideoEditerWrapper.getInstance().getEditer();
                editer.setBGMVolume(volume);
            }

            @Override
            public void onBGMTimeChanged(long startTime, long endTime) {
                mDraftEditer.setBgmStartTime(startTime);
                mDraftEditer.setBgmEndTime(endTime);

                onSetBGMStartTime(startTime, endTime);
                if (mTCBGMPannel != null) {
                    mTCBGMPannel.updateBGMStartTime(startTime);
                }
            }

            @Override
            public void onClickReplace() {
                chooseBGM();
            }

            @Override
            public void onClickDelete() {
                mDraftEditer.setBgmPath(null);

                TXVideoEditer editer = TCVideoEditerWrapper.getInstance().getEditer();
                editer.setBGM(null);

                mTCBGMPannel.setMusicName("");
            }

            @Override
            public void onClickConfirm() {
            }

            @Override
            public void onClickVoiceChanger(int type) {
            }

            @Override
            public void onClickReverb(int type) {
            }
        });
    }

}
