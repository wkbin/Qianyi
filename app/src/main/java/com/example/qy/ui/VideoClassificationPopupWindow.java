package com.example.qy.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 18 日 下午 1:12
 * Description: 弹出选择视频菜单
 */
public class VideoClassificationPopupWindow extends PopupWindow implements View.OnClickListener {
    private Activity activity;
    private View mMenuView;
    private LinearLayout li_tourism;
    private ImageView iv_food,iv_food2;
    private ImageView iv_tourism,iv_tourism2;
    private ImageView iv_cancel;

    //接口
    public interface OnPopWindowClickListener {
        void onPopWindowClickListener(View view);
    }
    private OnPopWindowClickListener listener;

    public VideoClassificationPopupWindow(Activity activity,OnPopWindowClickListener listener){
        this.activity = activity;
        this.listener = listener;
        initView(activity,listener);
    }

    public void show() {
        this.showAsDropDown(mMenuView);
    }



    private void initView(Activity activity,OnPopWindowClickListener listener){

        this.listener = listener;
        initViewSetting(activity);
        this.setContentView(mMenuView);

        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);


        this.setClippingEnabled(false);

        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xffffffff);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        startAnimation();
    }

    private void initViewSetting(Activity context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.video_classification_item, null);


        iv_food = mMenuView.findViewById(R.id.iv_food);
        iv_cancel = mMenuView.findViewById(R.id.iv_cancel);

        iv_food2 = mMenuView.findViewById(R.id.iv_food2);
        li_tourism = mMenuView.findViewById(R.id.li_tourism);


        iv_tourism2 = mMenuView.findViewById(R.id.iv_tourism2);
        iv_tourism = mMenuView.findViewById(R.id.iv_tourism);

        iv_cancel.setOnClickListener(this);

    }

    // 进去界面动画
    private void startAnimation(){
        //最下面的添加按钮旋转按钮动画
        RotateAnimation rotateAnimation = new RotateAnimation(0, 90,      RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(600);
        rotateAnimation.setInterpolator(new BounceInterpolator());
        rotateAnimation.setFillAfter(true);
        iv_cancel.setAnimation(rotateAnimation);


        int location[] = new int[2];
        int location2[] = new int[2];
        iv_tourism.getLocationOnScreen(location);
        iv_tourism2.getLocationOnScreen(location);
        int height1 = location[1];
        int height2 = location2[1];

        Log.d("666","height1 == "+height1+",height2 == "+height2);

        TranslateAnimation ta = new TranslateAnimation(0,0,0,-(height1-height2));
        // 设置动画时长
        ta.setDuration(400);
        iv_tourism2.setAnimation(ta);




    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if(Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_cancel:
                this.dismiss();
                break;
        }
    }
}
