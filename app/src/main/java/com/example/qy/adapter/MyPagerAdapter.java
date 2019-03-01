package com.example.qy.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragmentList;
        public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragmentList = fragments;
        }
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }
        @Override
        public int getCount() {
            return fragmentList.size();
        }
}
