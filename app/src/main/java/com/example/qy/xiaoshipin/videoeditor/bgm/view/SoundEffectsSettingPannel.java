package com.example.qy.xiaoshipin.videoeditor.bgm.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


import com.example.qy.R;
import com.tencent.ugc.TXRecordCommon;


public class SoundEffectsSettingPannel extends RelativeLayout implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {
    private Context mContext;
    private SeekBar mMicVolumeSeekBar;
    private int mMicVolume = 100;

    private SoundEffectsSettingPannelListener mSoundEffectsSettingPannelListener;

    private Button mBtnConfirm;

    private int mLastReverbIndex;
    private int mLastVoiceChangerIndex;

    public SoundEffectsSettingPannel(Context context) {
        super(context);
        init(context);
    }

    public SoundEffectsSettingPannel(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SoundEffectsSettingPannel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.layout_sound_effects, this);
        mMicVolumeSeekBar = (SeekBar) findViewById(R.id.seekbar_mic_volume);
        mMicVolumeSeekBar.setOnSeekBarChangeListener(this);


        mBtnConfirm = (Button) findViewById(R.id.btn_bgm_confirm);
        mBtnConfirm.setOnClickListener(this);

        findViewById(R.id.btn_reverb_default).setOnClickListener(this);
        findViewById(R.id.btn_reverb_1).setOnClickListener(this);
        findViewById(R.id.btn_reverb_2).setOnClickListener(this);
        findViewById(R.id.btn_reverb_3).setOnClickListener(this);
        findViewById(R.id.btn_reverb_4).setOnClickListener(this);
        findViewById(R.id.btn_reverb_5).setOnClickListener(this);
        findViewById(R.id.btn_reverb_6).setOnClickListener(this);

        findViewById(R.id.btn_voicechanger_default).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_1).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_2).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_3).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_4).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_6).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_7).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_8).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_9).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_10).setOnClickListener(this);
        findViewById(R.id.btn_voicechanger_11).setOnClickListener(this);

        setDefaultRevertAndVoiceChange();
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar.getId() == R.id.seekbar_mic_volume) {
            mMicVolume = progress;
            if (mSoundEffectsSettingPannelListener != null) {
                mSoundEffectsSettingPannelListener.onMicVolumeChanged(mMicVolume / (float) 100);
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setSoundEffectsSettingPannelListener(SoundEffectsSettingPannelListener soundEffectsSettingPannelListener) {
        mSoundEffectsSettingPannelListener = soundEffectsSettingPannelListener;
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bgm_confirm:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickConfirm();
                }
                break;
            case R.id.btn_reverb_default:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickReverb(TXRecordCommon.VIDOE_REVERB_TYPE_0);
                }
                break;
            case R.id.btn_reverb_1:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickReverb(TXRecordCommon.VIDOE_REVERB_TYPE_1);
                }
                break;
            case R.id.btn_reverb_2:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickReverb(TXRecordCommon.VIDOE_REVERB_TYPE_2);
                }
                break;
            case R.id.btn_reverb_3:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickReverb(TXRecordCommon.VIDOE_REVERB_TYPE_3);
                }
                break;
            case R.id.btn_reverb_4:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickReverb(TXRecordCommon.VIDOE_REVERB_TYPE_4);
                }
                break;
            case R.id.btn_reverb_5:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickReverb(TXRecordCommon.VIDOE_REVERB_TYPE_5);
                }
                break;
            case R.id.btn_reverb_6:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickReverb(TXRecordCommon.VIDOE_REVERB_TYPE_6);
                }
                break;

            case R.id.btn_voicechanger_default:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_0);
                }
                break;
            case R.id.btn_voicechanger_1:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_1);
                }
                break;
            case R.id.btn_voicechanger_2:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_2);
                }
                break;
            case R.id.btn_voicechanger_3:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_3);
                }
                break;
            case R.id.btn_voicechanger_4:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_4);
                }
                break;
            case R.id.btn_voicechanger_6:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_6);
                }
                break;
            case R.id.btn_voicechanger_7:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_7);
                }
                break;
            case R.id.btn_voicechanger_8:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_8);
                }
                break;
            case R.id.btn_voicechanger_9:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_9);
                }
                break;
            case R.id.btn_voicechanger_10:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_10);
                }
                break;
            case R.id.btn_voicechanger_11:
                if (mSoundEffectsSettingPannelListener != null) {
                    mSoundEffectsSettingPannelListener.onClickVoiceChanger(TXRecordCommon.VIDOE_VOICECHANGER_TYPE_11);
                }
                break;
        }


        if (v.getId() != mLastReverbIndex &&
                (v.getId() == R.id.btn_reverb_default || v.getId() == R.id.btn_reverb_1 ||
                        v.getId() == R.id.btn_reverb_2 || v.getId() == R.id.btn_reverb_3 ||
                        v.getId() == R.id.btn_reverb_4 || v.getId() == R.id.btn_reverb_5 ||
                        v.getId() == R.id.btn_reverb_6)) {   // 混响
            v.setSelected(true);

            View lastV = findViewById(mLastReverbIndex);
            if (null != lastV) {
                lastV.setSelected(false);
            }

            mLastReverbIndex = v.getId();

        } else if (v.getId() != mLastVoiceChangerIndex &&
                (v.getId() == R.id.btn_voicechanger_default || v.getId() == R.id.btn_voicechanger_1 || v.getId() == R.id.btn_voicechanger_2
                        || v.getId() == R.id.btn_voicechanger_3 || v.getId() == R.id.btn_voicechanger_4
                        || v.getId() == R.id.btn_voicechanger_6 || v.getId() == R.id.btn_voicechanger_7
                        || v.getId() == R.id.btn_voicechanger_8 || v.getId() == R.id.btn_voicechanger_9
                        || v.getId() == R.id.btn_voicechanger_10 || v.getId() == R.id.btn_voicechanger_11)) {  // 变声

            v.setSelected(true);

            View lastV = findViewById(mLastVoiceChangerIndex);
            if (null != lastV) {
                lastV.setSelected(false);
            }

            mLastVoiceChangerIndex = v.getId();
        }
    }

    private void setDefaultRevertAndVoiceChange() {
        TextView btnReverbDefalult = (TextView) findViewById(R.id.btn_reverb_default);
        btnReverbDefalult.setSelected(true);
        mLastReverbIndex = R.id.btn_reverb_default;

        TextView btnVoiceChangerDefault = (TextView) findViewById(R.id.btn_voicechanger_default);
        btnVoiceChangerDefault.setSelected(true);
        mLastVoiceChangerIndex = R.id.btn_voicechanger_default;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        return true;
    }

    public interface SoundEffectsSettingPannelListener {

        void onMicVolumeChanged(float volume);

        void onClickConfirm();

        void onClickVoiceChanger(int type);

        void onClickReverb(int type);
    }
}
