package com.example.qy.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.qy.R;
import com.example.qy.adapter.HomeAdapter;
import com.example.qy.ui.ViewPagerLayoutManager;
import com.example.qy.utils.ToastUtils;
import com.pili.pldroid.player.widget.PLVideoView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    private String TAG = "HomeFragment";
    private RecyclerView rv_home;
    private List<String> list;
    private ImageView iv_home,iv_search;
    private ViewPagerLayoutManager viewPagerLayoutManager;
    private String [] plays = {
            "http://192.168.10.6:8080/QianYi/views/1.mp4",
            "http://192.168.10.6:8080/QianYi/views/2.mp4",
            "http://192.168.10.6:8080/QianYi/views/3.mp4",
            "http://192.168.10.6:8080/QianYi/views/4.mp4",
            "http://192.168.10.6:8080/QianYi/views/5.mp4",
            "http://192.168.10.6:8080/QianYi/views/6.mp4",
            "http://192.168.10.6:8080/QianYi/views/7.mp4",
            "http://192.168.10.6:8080/QianYi/views/8.mp4",
            "http://192.168.10.6:8080/QianYi/views/9.mp4",
            "http://192.168.10.6:8080/QianYi/views/10.mp4"
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView(){
        iv_home = getActivity().findViewById(R.id.iv_home);
        iv_search = getActivity().findViewById(R.id.iv_search);
        iv_home.setBackgroundResource(R.mipmap.ic_home);
        iv_search.setBackgroundResource(R.mipmap.ic_search);
        rv_home = getActivity().findViewById(R.id.rv_home);
        list = new ArrayList<>();
        for (String s:plays){
            list.add(s);
        }
        viewPagerLayoutManager = new ViewPagerLayoutManager(getActivity(),OrientationHelper.VERTICAL);
        rv_home.setLayoutManager(viewPagerLayoutManager);
        HomeAdapter adapter = new HomeAdapter(getActivity(),list);
        rv_home.setAdapter(adapter);


        adapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                View itemView = rv_home.getChildAt(0);
                PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
                if (plVideoView.isPlaying()){
                    pausePlay();
                }else{
                    startPlay();
                }

            }
        });

        viewPagerLayoutManager.setOnViewPagerListener(new ViewPagerLayoutManager.OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                startPlay();
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                Log.e(TAG,"释放位置:"+position +" 下一页:"+isNext);
                // 要释放的视频
                pausePlay(isNext?0:1);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                Log.e(TAG,"选中位置:"+position+"  是否是滑动到底部:"+isBottom);
                startPlay();
            }
        });

    }

    public void startPlay(){
        View itemView = rv_home.getChildAt(0);
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
        plVideoView.start();
    }
    public void pausePlay(int index){
        View itemView = rv_home.getChildAt(index);
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
        plVideoView.pause();
    }
    public void pausePlay(){
        View itemView = rv_home.getChildAt(0);
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
        plVideoView.pause();
    }

    @Override
    public void onPause() {
        super.onPause();

        pausePlay();
    }

    @Override
    public void onStart() {
        super.onStart();
        View itemView = rv_home.getChildAt(0);
        if (itemView == null)
            return;
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
        if (!plVideoView.isPlaying())
            startPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        pausePlay();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        View itemView = rv_home.getChildAt(0);
        PLVideoView plVideoView = itemView.findViewById(R.id.PLvv_play);
        plVideoView.stopPlayback();
    }
}
