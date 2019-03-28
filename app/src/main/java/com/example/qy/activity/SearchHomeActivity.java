package com.example.qy.activity;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.activity.fragment.search.CommodityFragment;
import com.example.qy.activity.fragment.search.ShortVideoFragment;
import com.example.qy.activity.fragment.search.StrategyFragment;
import com.example.qy.activity.fragment.search.UserFragment;
import com.example.qy.adapter.MyPagerAdapter;
import com.example.qy.whs.BaseActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ClipPagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class SearchHomeActivity extends BaseActivity {
    private MagicIndicator magic_indicator;
    private ViewPager view_pager;
    private List<Fragment> fragments;
    private TextView tv_cancel;
    private String titles[] = {"短视频","商品","攻略","用户"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_home);

        tv_cancel = findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(v->{
            finish();
        });

        magic_indicator = findViewById(R.id.magic_indicator);
        view_pager = findViewById(R.id.view_pager);

        fragments = new ArrayList<>();

        fragments.add(new ShortVideoFragment());
        fragments.add(new CommodityFragment());
        fragments.add(new StrategyFragment());
        fragments.add(new UserFragment());

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(),fragments,titles);
        view_pager.setAdapter(adapter);

        CommonNavigator commonNavigator = new CommonNavigator(this);
        // 自适应
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return titles == null ? 0 : titles.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int i) {
                ClipPagerTitleView clipPagerTitleView = new ClipPagerTitleView(context);
                clipPagerTitleView.setText(titles[i]);


                clipPagerTitleView.setTextColor(Color.parseColor("#333333"));
                clipPagerTitleView.setClipColor(Color.parseColor("#66333333"));


                clipPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        view_pager.setCurrentItem(i);
                    }
                });

                return clipPagerTitleView;
            }
            @Override
            public IPagerIndicator getIndicator(Context context) {
                return null;    // 没有指示器，因为title的指示作用已经很明显了
            }
        });

        magic_indicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(magic_indicator,view_pager);

    }
}
