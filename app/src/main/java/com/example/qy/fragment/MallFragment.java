package com.example.qy.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.qy.R;
import com.example.qy.activity.AttractionsHomeActivity;
import com.example.qy.activity.MainActivity;
import com.example.qy.activity.SpecialtyListActivity;
import com.example.qy.adapter.SeckillAdapter;
import com.example.qy.bean.Seckill;
import com.example.qy.fragment.mallfragment.HotFragment;
import com.example.qy.ui.RoundImageView;
import com.example.qy.utils.ImageUtils;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;


/**
 * Author: 王克斌
 * Date: 2019 年 03 月 07 日 上午 9:25
 * Description:
 */
public class MallFragment extends Fragment implements View.OnClickListener {
    private String[] images = {
            "http://192.168.0.112:8080/QianYi/views/timp.jpg",
            "http://192.168.0.112:8080/QianYi/views/timg2.jpg",
            "http://192.168.0.112:8080/QianYi/views/timg3.jpg",
    };
    public List<Seckill> list;
    private BGABanner banner_guide_content;
    private ImageView iv_reproduction;
    private RoundImageView iv_top;
    private ImageView iv_menu;
    private RecyclerView rl_seckill;
    private HotFragment hotFragment;
    private TextView tv_hot_recommend, tv_guess_like;
    private View v_hot_recommend, v_guess_like;

    private LinearLayout li_title_bar;
    private NestedScrollView nsv_mall;

    private float py = 0;

    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mall, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getActivity().findViewById(R.id.li_specialty).setOnClickListener(this);
        getActivity().findViewById(R.id.li_attractions).setOnClickListener(this);

        li_title_bar = getActivity().findViewById(R.id.li_title_bar);
        li_title_bar.setAlpha(0);
        nsv_mall = getActivity().findViewById(R.id.nsv_mall);


        nsv_mall.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                if (scrollY > 540) {
                    li_title_bar.setAlpha(1);
                    banner_guide_content.stopAutoPlay();
                } else if (scrollY > 240) {
                    py = (scrollY - 240) / 1000f * 3;
                    li_title_bar.setAlpha(py);
                    banner_guide_content.startAutoPlay();
                } else {
                    li_title_bar.setAlpha(0);
                }
            }
        });

        tv_hot_recommend = getActivity().findViewById(R.id.tv_hot_recommend);
        tv_guess_like = getActivity().findViewById(R.id.tv_guess_like);
        v_hot_recommend = getActivity().findViewById(R.id.v_hot_recommend);
        v_guess_like = getActivity().findViewById(R.id.v_guess_like);
        tv_hot_recommend.setOnClickListener(this);
        tv_guess_like.setOnClickListener(this);

        iv_reproduction = getActivity().findViewById(R.id.iv_reproduction);
        rl_seckill = getActivity().findViewById(R.id.rl_seckill);

        iv_menu = getActivity().findViewById(R.id.iv_menu);

        MainActivity mainActivity = (MainActivity) getActivity();
        iv_menu.setOnClickListener(v -> {
            mainActivity.dl_mall.openDrawer(GravityCompat.START);
        });


        banner_guide_content = getActivity().findViewById(R.id.banner_guide_content);
        banner_guide_content.setAutoPlayAble(true);


//        Animation fadeInAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);


        banner_guide_content.setAdapter((banner, itemView, model, position) -> {
            Log.d("8888", "position == " + position);

            Glide.with(getActivity()).load(images[banner.getCurrentItem()]).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                    iv_reproduction.startAnimation(fadeInAnimation);
                    iv_reproduction.setImageBitmap(ImageUtils.rsBlur(getContext(), resource, 15, 0.75f));

                }
            });

//            iv_reproduction.setImageBitmap(ImageUtils.fastblur(getContext(),, 30));

            Log.d("8888", "model === " + model);

            Glide.with(getActivity())
                    .load(images[banner.getCurrentItem()])
                    .centerCrop()
                    .dontAnimate()
                    .into(iv_reproduction);

            Glide.with(getActivity())
                    .load(model)
                    .centerCrop()
                    .dontAnimate()
                    .into((ImageView) itemView);


        });


        banner_guide_content.setData(Arrays.asList(
                "http://192.168.0.112:8080/QianYi/views/timp.jpg",
                "http://192.168.0.112:8080/QianYi/views/timg2.jpg",
                "http://192.168.0.112:8080/QianYi/views/timg3.jpg"), null);

        list = new ArrayList<>();

        Seckill seckill = new Seckill();
        seckill.content = "江西赣南脐橙新鲜橙子生鲜水果直江西赣南";
        seckill.price = "¥88.00";
        for (int i = 0; i < 10; i++) {
            list.add(seckill);
        }
        SeckillAdapter adapter = new SeckillAdapter(getActivity(), list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rl_seckill.setLayoutManager(manager);
        rl_seckill.setAdapter(adapter);


        initHotFragment();


    }

    private void initHotFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (hotFragment == null) {
            hotFragment = new HotFragment();
            transaction.add(R.id.fm_mall, hotFragment);
        }
        transaction.show(hotFragment);
        transaction.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        banner_guide_content.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        banner_guide_content.stopAutoPlay();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_hot_recommend:
                break;
            case R.id.li_specialty:
                startActivity(new Intent(getActivity(), SpecialtyListActivity.class));
                break;
            case R.id.li_attractions:
                startActivity(new Intent(getActivity(), AttractionsHomeActivity.class));
                break;
        }
    }


}
