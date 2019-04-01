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
 * Date: 2019 年 03 月 29 日 上午 10:48
 * Description:
 */
public class FriendsFragment extends Fragment {
    private RecyclerView rc_friends;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_friends,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rc_friends = getActivity().findViewById(R.id.rc_friends);
        rc_friends.setLayoutManager(new LinearLayoutManager(getContext()));
        rc_friends.setAdapter(new FocusOnAdapter(getContext()));
    }
}
