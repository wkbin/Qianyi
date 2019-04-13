package com.example.qy.xiaoshipin.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 12 日 下午 5:18
 * Description:
 */
public class VideoCountDownDialog extends Dialog {
    private SeekBar sb_countdown_progress;
    private TextView tv_location;
    private int min;
    public VideoCountDownDialog(Context context) {
        super(context,R.style.custom_dialog2);
    }

    public VideoCountDownDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_video_countdown);
        // 设置对话框大小
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出位置
        getWindow().setGravity(Gravity.BOTTOM);

        tv_location = findViewById(R.id.tv_location);

        sb_countdown_progress = findViewById(R.id.sb_countdown_progress);
        sb_countdown_progress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_location.setText(progress+"s");
                if (progress < getMin()){
                    sb_countdown_progress.setProgress(getMin());
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public void setMin(int min){
        this.min = min;
        sb_countdown_progress.setProgress(min);
    }
    public int getMin(){
        return min;
    }

}
