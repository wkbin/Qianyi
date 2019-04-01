package com.example.qy.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qy.R;
import com.example.qy.adapter.FollowersAdapter;
import com.example.qy.adapter.MyPagerAdapter;
import com.example.qy.bean.Follwers;
import com.example.qy.fragment.focusfragment.FocusOnFragment;
import com.example.qy.fragment.focusfragment.FriendsFragment;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.TriangularPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

public class FocusFragment extends Fragment {
    private ViewPager vp_focus;
    private String titles[] = {"好友","关注"};
    private MagicIndicator magic_indicator;
    private List<Fragment> fragments;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_focus,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        vp_focus = getActivity().findViewById(R.id.vp_focus);
        magic_indicator = getActivity().findViewById(R.id.magic_indicator);

        fragments = new ArrayList<>();

        fragments.add(new FriendsFragment());
        fragments.add(new FocusOnFragment());

        MyPagerAdapter adapter = new MyPagerAdapter(getChildFragmentManager(),fragments);
        vp_focus.setAdapter(adapter);

        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
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

                        vp_focus.setCurrentItem(i);

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
        ViewPagerHelper.bind(magic_indicator,vp_focus);
    }
}
