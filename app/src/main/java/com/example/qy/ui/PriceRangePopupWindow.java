package com.example.qy.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 08 日 下午 1:24
 * Description: 价格区间Pop
 */
public class PriceRangePopupWindow extends PopupWindow implements View.OnClickListener {
    Context mContext;


    public PriceRangePopupWindow(Context context){
        super(context,null,R.style.PopTopAnim);
        this.mContext = context;
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.pop_price_range,null);
        setContentView(view);
        setBackgroundDrawable(new ColorDrawable(0));
        setFocusable(true);

        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //PopupWindow对象设置高度
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        view.findViewById(R.id.view_transparent).setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.view_transparent:
                dismiss();
                break;
        }
    }
}
