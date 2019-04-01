package com.example.qy.fragment.focusfragment;

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
import com.example.qy.adapter.FocusOnAdapter;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 29 日 上午 10:49
 * Description: 关注Fragment
 */
public class FocusOnFragment extends Fragment {
    private RecyclerView rc_on_focus;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_on_focus,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rc_on_focus = getActivity().findViewById(R.id.rc_on_focus);
        rc_on_focus.setLayoutManager(new LinearLayoutManager(getContext()));
        rc_on_focus.setAdapter(new FocusOnAdapter(getContext()));
    }
}
