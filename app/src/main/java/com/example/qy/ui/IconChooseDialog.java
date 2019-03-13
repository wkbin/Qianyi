package com.example.qy.ui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 04 日 下午 2:08
 * Description: 自定义头像选择对话框
 */
public class IconChooseDialog extends Dialog {

    private TextView tv_taking_pictures;    // 拍一张
    private TextView tv_gallery;    // 图库选取
    private TextView tv_cancel;     // 取消
    private Context context;

    public interface onClickListener{   // 定义点击事件监听器
        void onPicturesClick();
        void onGalleryClick();
        void onCancleClick();
    }

    private onClickListener onClickListener;

    public void setOnClickListener(IconChooseDialog.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public IconChooseDialog(Context context) {
        super(context);
        this.context = context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams params= getWindow().getAttributes();
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        params.gravity= Gravity.BOTTOM;
        getWindow().setAttributes(params);

        setContentView(R.layout.dialog_choose_icon);
        initView();
    }
    private void initView(){



        tv_taking_pictures = findViewById(R.id.tv_taking_pictures);
        tv_gallery = findViewById(R.id.tv_gallery);
        tv_cancel = findViewById(R.id.tv_cancel);

        tv_taking_pictures.setOnClickListener(v ->{
            onClickListener.onPicturesClick();
        });
        tv_gallery.setOnClickListener(v ->{
            onClickListener.onGalleryClick();
        });
        tv_cancel.setOnClickListener(v ->{
            onClickListener.onCancleClick();

        });



    }
}
