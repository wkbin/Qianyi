package com.example.qy.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.example.qy.R;
import com.example.qy.activity.fragment.special.AllFragment;
import com.example.qy.activity.fragment.special.RefundFragment;
import com.example.qy.activity.fragment.special.WaitDeliveryFragment;
import com.example.qy.activity.fragment.special.WaitEvaluationFragment;
import com.example.qy.activity.fragment.special.WaitPaymentFragment;
import com.example.qy.activity.fragment.special.WaitTakeDeliveryOfGoodsFragment;
import com.example.qy.adapter.MyPagerAdapter;
import com.example.qy.whs.BaseActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class SpecialOrdersActivity extends BaseActivity {
    private ViewPager vp_special;
    private String[] titles = {"全部","待付款","待发货","待收货","待评价","退款"};
    private List<Fragment> list;
    private MagicIndicator magic_indicator;
    private ImageView iv_alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special_orders);

        init("特产订单");

        vp_special = findViewById(R.id.vp_special);
        magic_indicator = findViewById(R.id.magic_indicator);
        list = new ArrayList<>();

        list.add(new AllFragment());
        list.add(new WaitPaymentFragment());
        list.add(new WaitDeliveryFragment());
        list.add(new WaitTakeDeliveryOfGoodsFragment());
        list.add(new WaitEvaluationFragment());
        list.add(new RefundFragment());

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),list);
        vp_special.setAdapter(adapter);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                //设置Magicindicator的一种标题模式， 标题模式有很多种，这是最基本的一种
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(titles[i]);
                simplePagerTitleView.setTextSize(14);
                //设置被选中的item颜色
                simplePagerTitleView.setSelectedColor(Color.BLACK);
                //设置未被选中item颜色
                simplePagerTitleView.setNormalColor(Color.parseColor("#661A1A1A"));


                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        vp_special.setCurrentItem(i);

                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.parseColor("#3DB2A3"));
                return indicator;
            }
        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator,vp_special);

        findViewById(R.id.iv_alert).setOnClickListener(v -> {
            startActivity(new Intent(this,SpecialtyOrderDetailsActivity.class));
        });

    }
}
