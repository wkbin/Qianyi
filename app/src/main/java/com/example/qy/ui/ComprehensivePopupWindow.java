package com.example.qy.ui;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 08 日 下午 3:16
 * Description: 综合排序
 */
public class ComprehensivePopupWindow extends PopupWindow implements View.OnClickListener {
    private View view_transparent;
    private TextView tv_comprehensive_ranking,tv_top_priority,tv_top_to_bottom,tv_bottom_to_top;
    private ImageView iv_comprehensive_ranking,iv_top_priority,iv_top_to_bottom,iv_bottom_to_top;

    public ComprehensivePopupWindow(Context context){
        super(context,null,R.style.PopTopAnim);
        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = mInflater.inflate(R.layout.pop_comprehensive,null);
        setContentView(view);
        setBackgroundDrawable(new ColorDrawable(0));
        setFocusable(true);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //PopupWindow对象设置高度
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置遮罩层

        view_transparent = view.findViewById(R.id.view_transparent);
        view_transparent.setOnClickListener(v -> {
            dismiss();
        });

        tv_comprehensive_ranking = view.findViewById(R.id.tv_comprehensive_ranking);
        tv_top_priority = view.findViewById(R.id.tv_top_priority);
        tv_top_to_bottom = view.findViewById(R.id.tv_top_to_bottom);
        tv_bottom_to_top = view.findViewById(R.id.tv_bottom_to_top);

        iv_comprehensive_ranking = view.findViewById(R.id.iv_comprehensive_ranking);
        iv_top_priority = view.findViewById(R.id.iv_top_priority);
        iv_top_to_bottom = view.findViewById(R.id.iv_top_to_bottom);
        iv_bottom_to_top = view.findViewById(R.id.iv_bottom_to_top);


        view.findViewById(R.id.rl_comprehensive_ranking).setOnClickListener(this);
        view.findViewById(R.id.rl_top_priority).setOnClickListener(this);
        view.findViewById(R.id.rl_top_to_bottom).setOnClickListener(this);
        view.findViewById(R.id.rl_bottom_to_top).setOnClickListener(this);
    }

    private void init(){
        tv_comprehensive_ranking.setTextColor(Color.parseColor("#B3B3B3"));
        tv_top_priority.setTextColor(Color.parseColor("#B3B3B3"));
        tv_top_to_bottom.setTextColor(Color.parseColor("#B3B3B3"));
        tv_bottom_to_top.setTextColor(Color.parseColor("#B3B3B3"));

        iv_comprehensive_ranking.setVisibility(View.INVISIBLE);
        iv_top_priority.setVisibility(View.INVISIBLE);
        iv_top_to_bottom.setVisibility(View.INVISIBLE);
        iv_bottom_to_top.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        init();
        switch (v.getId()){
            // 遮罩层
            case R.id.view_transparent:
                dismiss();
                break;
            // 综合排序
            case R.id.rl_comprehensive_ranking:
                tv_comprehensive_ranking.setTextColor(Color.parseColor("#3db2a3"));
                iv_comprehensive_ranking.setVisibility(View.VISIBLE);
                break;
            // 热门优先
            case R.id.rl_top_priority:
                tv_top_priority.setTextColor(Color.parseColor("#3db2a3"));
                iv_top_priority.setVisibility(View.VISIBLE);
                break;
            // 从高到底
            case R.id.rl_top_to_bottom:
                tv_top_to_bottom.setTextColor(Color.parseColor("#3db2a3"));
                iv_top_to_bottom.setVisibility(View.VISIBLE);
                break;
            // 从低到高
            case R.id.rl_bottom_to_top:
                tv_bottom_to_top.setTextColor(Color.parseColor("#3db2a3"));
                iv_bottom_to_top.setVisibility(View.VISIBLE);
                break;
        }
    }
}
