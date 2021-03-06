package com.example.qy.activity.fragment.assets;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;
import com.example.qy.adapter.AssetsAdapter;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 01 日 下午 4:56
 * Description:
 */
public class GiftFragment extends Fragment {
    private RecyclerView rc_gift;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_gift,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rc_gift = getActivity().findViewById(R.id.rc_gift);
        rc_gift.setLayoutManager(new LinearLayoutManager(getContext()));
        rc_gift.setAdapter(new AssetsAdapter(getContext()));
    }
}
