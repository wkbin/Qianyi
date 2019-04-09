package com.example.qy.ui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import com.airbnb.lottie.LottieAnimationView;
import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 01 日 下午 2:07
 * Description: 加载动画对话框
 */
public class LoadingDialog extends Dialog {
    private LottieAnimationView animation_view;
    public LoadingDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setDimAmount(0f);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        setContentView(R.layout.loading_dialog);

        animation_view = findViewById(R.id.animation_view);
        animation_view.setImageAssetsFolder("images");
        animation_view.setAnimation("data.json");
//        animation_view.useHardwareAcceleration(true);
//        animation_view.playAnimation();


        setCancelable(false);
    }
}
