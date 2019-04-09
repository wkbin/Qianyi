package com.example.qy.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.activity.fragment.search.adapter.CommodityAdapter;
import com.example.qy.ui.ComprehensivePopupWindow;
import com.example.qy.ui.FastScrollLinearLayoutManager;
import com.example.qy.ui.PriceRangePopupWindow;
import com.example.qy.whs.BaseActivity;

public class SpecialtyListActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rc_specialty_list;
    private RelativeLayout rl_condition;


    private TextView tv_price;
    private ImageView iv_price;
    private ImageView iv_comprehensive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_list);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.civ_top).setOnClickListener(this);
        findViewById(R.id.li_price).setOnClickListener(this);
        findViewById(R.id.li_comprehensive).setOnClickListener(this);

        tv_price = findViewById(R.id.tv_price);
        iv_price = findViewById(R.id.iv_price);
        iv_comprehensive = findViewById(R.id.iv_comprehensive);

        rc_specialty_list = findViewById(R.id.rc_specialty_list);
        rl_condition = findViewById(R.id.rl_condition);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rc_specialty_list.setLayoutManager(manager);
        rc_specialty_list.setAdapter(new CommodityAdapter(this));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.civ_top:
                rc_specialty_list.smoothScrollToPosition(0);
                break;
            case R.id.li_price:
                PriceRangePopupWindow popupWindow  = new PriceRangePopupWindow(SpecialtyListActivity.this);;
                popupWindow.showAsDropDown(rl_condition);
                if (popupWindow.isShowing()) {
                    tv_price.setTextColor(Color.parseColor("#3db2a3"));
                    iv_price.setImageResource(R.mipmap.shangfan);
                }
                popupWindow.setOnDismissListener(() -> {
                    tv_price.setTextColor(Color.parseColor("#333333"));
                    iv_price.setImageResource(R.mipmap.xialla);
                });
                break;
            case R.id.li_comprehensive:
                ComprehensivePopupWindow comprehensivePopupWindow = new ComprehensivePopupWindow(this);
                comprehensivePopupWindow.showAsDropDown(rl_condition);
                if (comprehensivePopupWindow.isShowing()) {
                    iv_comprehensive.setImageResource(R.mipmap.shangfan);
                }
                comprehensivePopupWindow.setOnDismissListener(() -> {
                    iv_comprehensive.setImageResource(R.mipmap.xialla01);
                });
                break;
        }
    }
}
