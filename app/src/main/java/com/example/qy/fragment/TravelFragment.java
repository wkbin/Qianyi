package com.example.qy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;
import com.example.qy.adapter.TravelAdapter;
import com.example.qy.bean.Travel;

import java.util.ArrayList;
import java.util.List;

public class TravelFragment extends Fragment {
    private RecyclerView rc_travel;
    private List<Travel> travels;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_travel,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }

    private void initView(){
        rc_travel = getActivity().findViewById(R.id.rc_travel);
        travels = new ArrayList<>();
        for (int i = 0;i<20;i++){
            Travel travel = new Travel();
            travel.setIconPath("http://tva3.sinaimg.cn/crop.0.0.509.509.1024/006xygXHgw1f8waenlsdzj30e60e60up.jpg");
            travel.setImagePath("https://img2.woyaogexing.com/2018/12/31/645fc061ebfe8fa0!600x600.jpg");
            travel.setTravelNumber("99");
            travel.setUserName("我的昵称");
            travel.setTravelText("视频介绍文字视频介绍文字视频介绍文字");
            travels.add(travel);
        }
        TravelAdapter adapter = new TravelAdapter(getActivity(),travels);
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rc_travel.setLayoutManager(layoutManager);
        rc_travel.setAdapter(adapter);
    }
}
