package com.example.qy.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.widget.TextView;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 13 日 下午 3:59
 * Description: 视频分享
 */
public class MyVideoShareSheetBottomDialog extends BottomSheetDialog{

    private TextView tv_cancel;

    public MyVideoShareSheetBottomDialog(@NonNull Context context) {
        super(context);
    }

    public MyVideoShareSheetBottomDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    protected MyVideoShareSheetBottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setWindowAnimations(R.style.DialogBottomAnim);
        setContentView(R.layout.my_bottom_dialog_video_share);

        tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(v->{
            dismiss();
        });
    }
}
