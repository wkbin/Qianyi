package com.example.qy.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.qy.R;
import com.example.qy.activity.FocusActivity;
import com.example.qy.activity.IntegralTaskActivity;
import com.example.qy.activity.MessageActivity;
import com.example.qy.activity.MyHomePageActivity;
import com.example.qy.activity.SettingsActivity;
import com.example.qy.activity.ShippingAddressActivity;
import com.example.qy.activity.UpdatePhoneActivity;
import com.example.qy.bean.UserInfo;
import com.example.qy.utils.ImageUtils;
import com.example.qy.whs.MyApplication;




public class MyFragment extends Fragment implements View.OnClickListener {
    private LinearLayout li_home_page;
    private de.hdodenhof.circleimageview.CircleImageView cv_my_icon;
    private TextView tv_my_nickname;
    private TextView tv_my_id;
    private ImageView iv_beijing;
    private TextView tv_my_signature;
    private TextView tv_my_attention;
    private TextView tv_my_fans;
    private ImageView iv_my_settings;
    private LinearLayout li_my_attention,li_my_fans;
    private LottieAnimationView lav_heart;
    private LinearLayout li_binding_phone;
    private LinearLayout li_message;
    private LinearLayout li_shipping_address;
    private TextView tv_binding;
    private String phone;
    private ImageView iv_integraltask;
    private RelativeLayout rl_home_page;





    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my2,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cv_my_icon = getActivity().findViewById(R.id.cv_my_icon);
        tv_my_nickname = getActivity().findViewById(R.id.tv_my_nickname);
        tv_my_id = getActivity().findViewById(R.id.tv_my_id);
        iv_beijing = getActivity().findViewById(R.id.iv_beijing);

//        tv_my_signature = getActivity().findViewById(R.id.tv_my_signature);
        tv_my_attention = getActivity().findViewById(R.id.tv_my_attention);
        tv_my_fans = getActivity().findViewById(R.id.tv_my_fans);
//        lav_heart = getActivity().findViewById(R.id.lav_heart);
//        tv_binding = getActivity().findViewById(R.id.tv_binding);
        li_binding_phone = getActivity().findViewById(R.id.li_binding_phone);
        li_message = getActivity().findViewById(R.id.li_message);
        li_shipping_address = getActivity().findViewById(R.id.li_shipping_address);
        iv_integraltask = getActivity().findViewById(R.id.iv_integraltask);
        rl_home_page = getActivity().findViewById(R.id.rl_home_page);
//
//        lav_heart.setImageAssetsFolder("images");
//        lav_heart.setAnimation("data.json");
//        // 开启硬件加速
//        lav_heart.useHardwareAcceleration(true);
//
//        li_home_page = getActivity().findViewById(R.id.li_home_page);
        iv_my_settings = getActivity().findViewById(R.id.iv_my_settings);
        iv_my_settings.setOnClickListener(this);
//        li_home_page.setOnClickListener(this);
//        lav_heart.setOnClickListener(this);
        li_my_attention = getActivity().findViewById(R.id.li_my_attention);
        li_my_fans = getActivity().findViewById(R.id.li_my_fans);
        li_my_attention.setOnClickListener(this);
        li_my_fans.setOnClickListener(this);
        li_binding_phone.setOnClickListener(this);
        li_message.setOnClickListener(this);
        li_shipping_address.setOnClickListener(this);
        iv_integraltask.setOnClickListener(this);
        rl_home_page.setOnClickListener(this);



        initData();
    }
    private void initData(){
        UserInfo userInfo = ((MyApplication)getActivity().getApplication()).getUserInfo();
        if (userInfo == null) return;
        Log.d("MyFragment","userInfo = "+userInfo.toString());
        phone = userInfo.phone;
//        if (TextUtils.isEmpty(phone)){
//            tv_binding.setText("未绑定");
//        }else{
//            tv_binding.setText("已绑定");
//        }
        tv_my_nickname.setText(userInfo.nickname);
        tv_my_id.setText("千艺号："+userInfo.qianyiID);
//        tv_my_signature.setText(userInfo.signature);
        tv_my_attention.setText(userInfo.follow);
        tv_my_fans.setText(userInfo.fans);

        Glide.with(getActivity()).load(userInfo.icon).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                cv_my_icon.setImageBitmap(resource);
                iv_beijing.setImageBitmap(ImageUtils.fastblur(getContext(),resource,50));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        UserInfo userInfo = ((MyApplication)getActivity().getApplication()).getUserInfo();
        tv_my_nickname.setText(userInfo.nickname);
//        tv_my_signature.setText(userInfo.signature);
        tv_my_attention.setText(userInfo.follow);
        tv_my_fans.setText(userInfo.fans);
        Glide.with(getActivity()).load(userInfo.icon).into(cv_my_icon);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_home_page:
                Intent i = new Intent(getActivity(),MyHomePageActivity.class);
                startActivityForResult(i,1);
                break;
//            case R.id.li_home_page:
//                Intent i = new Intent(getActivity(),MyHomePageActivity.class);
//                startActivityForResult(i,1);
//                break;
            case R.id.iv_my_settings:
                startActivity(new Intent(getActivity(),SettingsActivity.class));
                break;
            case R.id.li_my_attention:
                Intent intent = new Intent(getActivity(),FocusActivity.class);
                intent.putExtra("type",1);
                startActivity(intent);
                break;
            case R.id.li_my_fans:
                Intent i1 = new Intent(getActivity(),FocusActivity.class);
                i1.putExtra("type",2);
                startActivity(i1);
                break;
            case R.id.iv_integraltask:
                startActivity(new Intent(getActivity(),IntegralTaskActivity.class));
                break;
//            case R.id.lav_heart:
//                startActivity(new Intent(getActivity(),IntegralTaskActivity.class));
//                break;
            case R.id.li_binding_phone:
                if (TextUtils.isEmpty(phone)){

                }else{
                    Intent updatePhoneIntent = new Intent(getActivity(),UpdatePhoneActivity.class);
                    updatePhoneIntent.putExtra("phone",phone);
                    startActivity(updatePhoneIntent);
                }
                break;
            case R.id.li_message:
                startActivity(new Intent(getActivity(),MessageActivity.class));
                break;
            case R.id.li_shipping_address:
                startActivity(new Intent(getActivity(),ShippingAddressActivity.class));
                break;
        }
    }
}
