package com.example.qy.activity.fragment.special;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qy.R;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 02 日 下午 1:49
 * Description: 待收货
 */
public class WaitTakeDeliveryOfGoodsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(getContext()).inflate(R.layout.fragment_special_take_delivery_of_goods,container,false);
    }
}
