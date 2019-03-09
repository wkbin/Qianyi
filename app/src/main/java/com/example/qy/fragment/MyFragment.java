package com.example.qy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.activity.FocusActivity;
import com.example.qy.activity.IntegralTaskActivity;
import com.example.qy.activity.MyHomePageActivity;
import com.example.qy.activity.SettingsActivity;
import com.example.qy.bean.UserInfo;
import com.example.qy.whs.MyApplication;
import com.jaeger.library.StatusBarUtil;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyFragment extends Fragment implements View.OnClickListener {
    private LinearLayout li_home_page;
    private de.hdodenhof.circleimageview.CircleImageView cv_my_icon;
    private TextView tv_my_nickname;
    private TextView tv_my_id;
    private TextView tv_my_signature;
    private TextView tv_my_attention;
    private TextView tv_my_fans;
    private ImageView iv_my_settings;
    private LinearLayout li_my_attention,li_my_fans;
    private LottieAnimationView lav_heart;

//    private CircleImageView civ_sign;


//    private Button btn_qr_code;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTransparent(getActivity());
        super.onActivityCreated(savedInstanceState);




        cv_my_icon = getActivity().findViewById(R.id.cv_my_icon);
        tv_my_nickname = getActivity().findViewById(R.id.tv_my_nickname);
        tv_my_id = getActivity().findViewById(R.id.tv_my_id);
        tv_my_signature = getActivity().findViewById(R.id.tv_my_signature);
        tv_my_attention = getActivity().findViewById(R.id.tv_my_attention);
        tv_my_fans = getActivity().findViewById(R.id.tv_my_fans);
        lav_heart = getActivity().findViewById(R.id.lav_heart);

        lav_heart.setImageAssetsFolder("images");
        lav_heart.setAnimation("data.json");
        // 开启硬件加速
        lav_heart.useHardwareAcceleration(true);

        li_home_page = getActivity().findViewById(R.id.li_home_page);
        iv_my_settings = getActivity().findViewById(R.id.iv_my_settings);
        iv_my_settings.setOnClickListener(this);
        li_home_page.setOnClickListener(this);
        lav_heart.setOnClickListener(this);
        li_my_attention = getActivity().findViewById(R.id.li_my_attention);
        li_my_fans = getActivity().findViewById(R.id.li_my_fans);
        li_my_attention.setOnClickListener(this);
        li_my_fans.setOnClickListener(this);



        initData();
    }
    private void initData(){
        UserInfo userInfo = ((MyApplication)getActivity().getApplication()).getUserInfo();
        if (userInfo == null) return;
        Log.d("MyFragment","userInfo = "+userInfo.toString());
        tv_my_nickname.setText(userInfo.nickname);
        tv_my_id.setText("千艺号："+userInfo.qianyiID);
        tv_my_signature.setText(userInfo.signature);
        tv_my_attention.setText(userInfo.follow);
        tv_my_fans.setText(userInfo.fans);
        Glide.with(getActivity()).load(userInfo.icon).into(cv_my_icon);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.li_home_page){
            startActivity(new Intent(getActivity(),MyHomePageActivity.class));
        }else if(v.getId() == R.id.iv_my_settings){
            startActivity(new Intent(getActivity(),SettingsActivity.class));
        }else if (v.getId() == R.id.li_my_attention){
            Intent intent = new Intent(getActivity(),FocusActivity.class);
            intent.putExtra("type",1);
            startActivity(intent);
        }else if (v.getId() == R.id.li_my_fans){
            startActivity(new Intent(getActivity(),FocusActivity.class));
        }
        else if(v.getId() == R.id.lav_heart){
            startActivity(new Intent(getActivity(),IntegralTaskActivity.class));
        }
    }
}
