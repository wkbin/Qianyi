package com.example.qy.fragment;

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
import com.example.qy.adapter.FollowersAdapter;
import com.example.qy.bean.Follwers;

import java.util.ArrayList;
import java.util.List;

public class FocusFragment extends Fragment {
//    private RecyclerView rl_focus;
//    private List<Follwers> lists;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_focus,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        rl_focus = getActivity().findViewById(R.id.rl_focus);
//        lists = new ArrayList<>();
//        Follwers follwers = new Follwers();
//        follwers.setIcon("https://pic.qqtn.com/up/2019-2/2019020508422893964.jpg");
//        follwers.setName("用户1");
//        follwers.setWorkAndFans("作品125 粉丝546");
//        lists.add(follwers);
//        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
//        FollowersAdapter adapter = new FollowersAdapter(getActivity(),lists);
//        rl_focus.setLayoutManager(manager);
//        rl_focus.setAdapter(adapter);
    }
}
