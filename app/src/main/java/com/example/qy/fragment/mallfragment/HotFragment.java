package com.example.qy.fragment.mallfragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;
import com.example.qy.adapter.MallHotAdapter;
import com.example.qy.bean.MallHot;
import com.example.qy.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 20 日 上午 10:15
 * Description: 热门推荐
 */
public class HotFragment extends Fragment {
    private RecyclerView rc_mall_hot;
    private List<MallHot> list;
    private MallHotAdapter adapter;


    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall_hot,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rc_mall_hot = getActivity().findViewById(R.id.rc_mall_hot);

        list = new ArrayList<>();

        for (int i = 0;i< 10 ; i++){
            list.add(new MallHot());
        }

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        adapter = new MallHotAdapter(getContext(),list);
        rc_mall_hot.setLayoutManager(manager);
        rc_mall_hot.setAdapter(adapter);





    }
}
