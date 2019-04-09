package com.example.qy.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.qy.R;
import com.example.qy.activity.fragment.assets.CardVoucherFragment;
import com.example.qy.activity.fragment.assets.GiftFragment;
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

public class MyAssetsActivity extends BaseActivity {
    private MagicIndicator magic_indicator;
    private ViewPager vp_assets;
    private String titles[] = {"活动礼品","我的卡券"};
    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_assets);

        init("我的资产");

        magic_indicator = findViewById(R.id.magic_indicator);
        vp_assets = findViewById(R.id.vp_assets);

        fragmentList = new ArrayList<>();
        fragmentList.add(new GiftFragment());
        fragmentList.add(new CardVoucherFragment());


        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList);
        vp_assets.setAdapter(adapter);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
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

                        vp_assets.setCurrentItem(i);

                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
//                indicator.setLineColor(Color.parseColor("#3DB2A3"));
                indicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                indicator.setColors(Color.parseColor("#3DB2A3"));
                return indicator;
            }
        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator,vp_assets);
    }
}
