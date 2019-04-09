package com.example.qy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.activity.FocusActivity;
import com.example.qy.activity.IntegralMallActivity;
import com.example.qy.activity.IntegralTaskActivity;
import com.example.qy.activity.MemberActivity;
import com.example.qy.activity.MessageActivity;
import com.example.qy.activity.MyHomePageActivity;
import com.example.qy.activity.MyOrderActivity;
import com.example.qy.activity.RecentVisitActivity;
import com.example.qy.activity.ScenicSpotOrdersActivity;
import com.example.qy.activity.SettingsActivity;
import com.example.qy.activity.ShippingAddressActivity;
import com.example.qy.activity.ShoppingCartActivity;
import com.example.qy.activity.SpecialOrdersActivity;
import com.example.qy.activity.UpdatePhoneActivity;
import com.example.qy.bean.UserInfo;
import com.example.qy.whs.MyApplication;

public class MyFragment extends Fragment implements View.OnClickListener {
    private de.hdodenhof.circleimageview.CircleImageView cv_my_icon;
    private TextView tv_my_nickname;
    private TextView tv_my_id;
    private TextView tv_my_attention;
    private TextView tv_my_fans;
    public String phone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my3,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        cv_my_icon = getActivity().findViewById(R.id.cv_my_icon);
        tv_my_nickname = getActivity().findViewById(R.id.tv_my_nickname);
        tv_my_id = getActivity().findViewById(R.id.tv_my_id);
        tv_my_attention = getActivity().findViewById(R.id.tv_my_attention);
        tv_my_fans = getActivity().findViewById(R.id.tv_my_fans);

        getActivity().findViewById(R.id.li_my_attention).setOnClickListener(this);
        getActivity().findViewById(R.id.li_my_fans).setOnClickListener(this);
        getActivity().findViewById(R.id.li_like).setOnClickListener(this);
        getActivity().findViewById(R.id.li_works).setOnClickListener(this);
        getActivity().findViewById(R.id.li_home_page).setOnClickListener(this);
        getActivity().findViewById(R.id.iv_my_settings).setOnClickListener(this);
        getActivity().findViewById(R.id.li_order).setOnClickListener(this);
        getActivity().findViewById(R.id.li_my_specialty).setOnClickListener(this);
        getActivity().findViewById(R.id.li_scenic_spot).setOnClickListener(this);
        getActivity().findViewById(R.id.rl_integral_mall).setOnClickListener(this);
        getActivity().findViewById(R.id.rl_integral_sign_in).setOnClickListener(this);
        getActivity().findViewById(R.id.rl_message).setOnClickListener(this);
        getActivity().findViewById(R.id.rl_recent_visit).setOnClickListener(this);
        getActivity().findViewById(R.id.rl_binding_phone).setOnClickListener(this);
        getActivity().findViewById(R.id.li_shipping_address).setOnClickListener(this);
        getActivity().findViewById(R.id.li_shopping_cart).setOnClickListener(this);
        getActivity().findViewById(R.id.iv_member).setOnClickListener(this);


        initData();
    }
    private void initData(){
        UserInfo userInfo = ((MyApplication)getActivity().getApplication()).getUserInfo();
        if (userInfo == null) return;
        Log.d("MyFragment","userInfo = "+userInfo.toString());
        phone = userInfo.phone;
        tv_my_nickname.setText(userInfo.nickname);
        tv_my_id.setText("千艺号："+userInfo.qianyiID);
        tv_my_attention.setText(userInfo.follow);
        tv_my_fans.setText(userInfo.fans);

        Glide.with(getActivity()).load(userInfo.icon).into(cv_my_icon);


    }

    @Override
    public void onStart() {
        super.onStart();
        UserInfo userInfo = ((MyApplication)getActivity().getApplication()).getUserInfo();
        if (userInfo != null){
            if (!TextUtils.isEmpty(userInfo.nickname)){
                tv_my_nickname.setText(userInfo.nickname);
            }
            if (!TextUtils.isEmpty(userInfo.follow)){
                tv_my_attention.setText(userInfo.follow);
            }
            if (!TextUtils.isEmpty(userInfo.fans)){
                tv_my_fans.setText(userInfo.fans);
            }
            if (!TextUtils.isEmpty(userInfo.icon)){
                Glide.with(getActivity()).load(userInfo.icon).into(cv_my_icon);
            }


        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_works:
            case R.id.li_like:
            case R.id.li_home_page:
                Intent i = new Intent(getActivity(),MyHomePageActivity.class);
                startActivityForResult(i,1);
                break;
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
            case R.id.rl_integral_sign_in:
                startActivity(new Intent(getActivity(),IntegralTaskActivity.class));
                break;
            case R.id.rl_binding_phone:
                if (TextUtils.isEmpty(phone)){

                }else{
                    Intent updatePhoneIntent = new Intent(getActivity(),UpdatePhoneActivity.class);
                    updatePhoneIntent.putExtra("phone",phone);
                    startActivity(updatePhoneIntent);
                }
                break;
            case R.id.rl_message:
                startActivity(new Intent(getActivity(),MessageActivity.class));
                break;
            case R.id.li_shipping_address:
                startActivity(new Intent(getActivity(),ShippingAddressActivity.class));
                break;
            case R.id.li_my_specialty:
                startActivity(new Intent(getActivity(),SpecialOrdersActivity.class));
                break;
            case R.id.li_scenic_spot:
                startActivity(new Intent(getActivity(),ScenicSpotOrdersActivity.class));
                break;
            case R.id.li_order:
                startActivity(new Intent(getActivity(),MyOrderActivity.class));
                break;
            case R.id.rl_recent_visit:
                startActivity(new Intent(getActivity(),RecentVisitActivity.class));
                break;
            case R.id.iv_member:
                startActivity(new Intent(getActivity(),MemberActivity.class));
                break;
            case R.id.rl_integral_mall:
                startActivity(new Intent(getActivity(),IntegralMallActivity.class));
                break;
            case R.id.li_shopping_cart:
                startActivity(new Intent(getActivity(),ShoppingCartActivity.class));
                break;
        }
    }
}
