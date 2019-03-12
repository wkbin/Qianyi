package com.example.qy.fragment;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.qy.R;
import com.example.qy.utils.ImageUtils;
import com.jaeger.library.StatusBarUtil;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 07 日 上午 9:25
 * Description:
 */
public class MallFragment extends Fragment {
    private DrawerLayout dl_mall;
    private Button btn_open;
    private ImageView iv_reproduction;
    private ImageView iv_top;
    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(getActivity());
        super.onActivityCreated(savedInstanceState);

        iv_reproduction = getActivity().findViewById(R.id.iv_reproduction);
        iv_top = getActivity().findViewById(R.id.iv_top);

        Glide.with(getActivity()).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=1772414185,1965589035&fm=26&gp=0.jpg").asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                iv_top.setImageBitmap(resource);
                Bitmap b = ImageUtils.fastblur(getActivity(),resource,40);
                iv_reproduction.setImageBitmap(b);
            }
        });




//        btn_open = getActivity().findViewById(R.id.btn_open);

//        btn_open.setOnClickListener(v-> {
//            dl_mall.openDrawer(GravityCompat.START);
//        });


    }
}
