package com.example.qy.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;

import com.example.qy.R;
import com.example.qy.activity.fragment.attractions.AllFragment;
import com.example.qy.activity.fragment.attractions.RefundFragment;
import com.example.qy.activity.fragment.attractions.WaitEvaluationFragment;
import com.example.qy.activity.fragment.attractions.WaitPaymentFragment;
import com.example.qy.activity.fragment.attractions.WaitUseFragment;
import com.example.qy.whs.BaseActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.CommonPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class ScenicSpotOrders extends BaseActivity {
    private MagicIndicator magic_indicator;
    private ViewPager view_pager;
    private List<Fragment> fragments;
    private String[] titles = {"全部","待付款","待使用","待评价","退款"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scenic_spot_orders);
        init("景点订单");

        magic_indicator = findViewById(R.id.magic_indicator);
        view_pager = findViewById(R.id.view_pager);

        fragments = new ArrayList<>();
        fragments.add(new AllFragment());
        fragments.add(new WaitPaymentFragment());
        fragments.add(new WaitUseFragment());
        fragments.add(new WaitEvaluationFragment());
        fragments.add(new RefundFragment());

        SttractionsPagerAdapter adapter = new SttractionsPagerAdapter(getSupportFragmentManager(),fragments);
        view_pager.setAdapter(adapter);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return titles == null ? 0 : titles.length;
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

                        view_pager.setCurrentItem(i);

                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                //设置标题指示器，也有多种,可不选，即没有标题指示器。
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(Color.parseColor("#ff4a42"),
                        Color.parseColor("#fcde64"),
                        Color.parseColor("#73e8f4"),
                        Color.parseColor("#76b0ff"),
                        Color.parseColor("#c683fe"),
                        Color.parseColor("#76b0ff"),
                        Color.parseColor("#c683fe"),
                        Color.parseColor("#76b0ff"),
                        Color.parseColor("#c683fe"));

                return indicator;
            }
        });
        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator,view_pager);
    }


    class SttractionsPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        public SttractionsPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragmentList = fragments;
        }
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }
        @Override
        public int getCount() {
            return titles.length;
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }

}
