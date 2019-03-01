package com.example.qy.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.qy.R;
import com.example.qy.activity.LoginActivity;
import com.example.qy.whs.MyApplication;
import com.jaeger.library.StatusBarUtil;

public class MainFragment extends Fragment implements View.OnClickListener{
    private HomeFragment mHomeFragment;
    private MyFragment mMyFragment;
    private FocusFragment mFocusFragment;
    private LinearLayout mLiHome,mLiFocus,mLiCamera,mLiShopping,mLiMy;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(getActivity(), null);
        View view = inflater.inflate(R.layout.fragment_main,container,false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
    }
    private void initView(){
        initFragment("home");
        mLiHome = getActivity().findViewById(R.id.li_home);
        mLiFocus = getActivity().findViewById(R.id.li_focus);
        mLiCamera = getActivity().findViewById(R.id.li_camera);
        mLiShopping = getActivity().findViewById(R.id.li_shopping);
        mLiMy = getActivity().findViewById(R.id.li_my);

        mLiHome.setOnClickListener(this);
        mLiMy.setOnClickListener(this);
        mLiFocus.setOnClickListener(this);
    }

        private void initFragment(String tag){
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction  = fragmentManager.beginTransaction();
        // 隐藏所有的fragment
        hideFragment(transaction);
        switch (tag){
            case "home":
                if (mHomeFragment == null){
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.fm_main,mHomeFragment);
                }
                transaction.show(mHomeFragment);
                break;
            case "my":
                if (mMyFragment == null){
                    mMyFragment = new MyFragment();
                    transaction.add(R.id.fm_main,mMyFragment);
                }
                transaction.show(mMyFragment);
                break;
            case "focus":
                if (mFocusFragment == null){
                    mFocusFragment = new FocusFragment();
                    transaction.add(R.id.fm_main, mFocusFragment);
                }
                transaction.show(mFocusFragment);
                break;
        }
        transaction.commit();
    }
    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mMyFragment != null) {
            transaction.hide(mMyFragment);
        }
        if (mFocusFragment != null){
            transaction.hide(mFocusFragment);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.li_home:
                initFragment("home");
                break;
            case R.id.li_focus:
                initFragment("focus");
                break;
            case R.id.li_camera:
                break;
            case R.id.li_shopping:
                break;
            case R.id.li_my:
                MyApplication application = (MyApplication) getActivity().getApplication();
                if (application.getUserInfo() != null){
                    initFragment("my");
                }else{
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }

                break;
        }
    }

}
