package com.example.qy.activity;


import android.os.Bundle;
import android.view.View;
import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class AccountSecurityActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_security);
        init("账号与安全");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
}
