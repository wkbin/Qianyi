package com.example.qy.activity.fragment.attractions;

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
import com.example.qy.activity.fragment.attractions.adapter.AllAdapter;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 23 日 下午 4:21
 * Description:
 */
public class WaitEvaluationFragment extends Fragment {
    private RecyclerView rc_wait_attraction;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_attractions_wait_evaluation,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rc_wait_attraction = getActivity().findViewById(R.id.rc_wait_attraction);
        AllAdapter adapter =new AllAdapter(getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());

        rc_wait_attraction.setLayoutManager(manager);
        rc_wait_attraction.setAdapter(adapter);

    }
}
