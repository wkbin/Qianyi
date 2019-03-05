package com.example.qy.ui;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 04 日 下午 5:30
 * Description: 性别选择对话框
 */
public class SexChooseDialog extends Dialog implements View.OnClickListener {
    public boolean isBoy = true;

    private RelativeLayout rl_nan;
    private RelativeLayout rl_nv;
    private Button btn_confirm;
    private TextView tv_quit;

    private TextView tv_nan;
    private TextView tv_nv;

    private ImageView iv_nan;
    private ImageView iv_nv;

    public interface OnClickListener{
        void onConfirmClick(boolean isBoy);
        void onCancelClick();
    }

    public OnClickListener onClickListener;
    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public SexChooseDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_sex);

        rl_nan = findViewById(R.id.rl_nan);
        rl_nv = findViewById(R.id.rl_nv);
        btn_confirm = findViewById(R.id.btn_confirm);
        tv_quit = findViewById(R.id.tv_quit);
        tv_nan = findViewById(R.id.tv_nan);
        tv_nv = findViewById(R.id.tv_nv);
        iv_nan = findViewById(R.id.iv_nan);
        iv_nv = findViewById(R.id.iv_nv);

        rl_nan.setOnClickListener(this);
        rl_nv.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        tv_quit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_nan:
                isBoy = true;
                tv_nan.setTextColor(Color.parseColor("#1a1a1a"));
                tv_nv.setTextColor(Color.parseColor("#999999"));
                iv_nan.setImageResource(R.mipmap.nandianji);
                iv_nv.setImageResource(R.mipmap.nvweidianji);
                break;
            case R.id.rl_nv:
                isBoy = false;
                tv_nan.setTextColor(Color.parseColor("#999999"));
                tv_nv.setTextColor(Color.parseColor("#1a1a1a"));
                iv_nan.setImageResource(R.mipmap.nanweidianji);
                iv_nv.setImageResource(R.mipmap.nvdianji);
                break;
            case R.id.btn_confirm:
                onClickListener.onConfirmClick(isBoy);
                break;
            case R.id.tv_quit:
                onClickListener.onCancelClick();
                break;
        }
    }
}
