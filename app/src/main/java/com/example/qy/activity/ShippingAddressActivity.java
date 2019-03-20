package com.example.qy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class ShippingAddressActivity extends BaseActivity implements View.OnClickListener {
    private ImageView action_bar_iv_left;
    private TextView action_bar_text;
    private TextView action_bar_iv_right;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);

        action_bar_text.setText("收货地址");
        action_bar_iv_right.setText("添加新地址");



        action_bar_iv_left.setOnClickListener(this);
        action_bar_iv_right.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_bar_iv_left:
                finish();
                break;
            case R.id.action_bar_iv_right:
                startActivity(new Intent(ShippingAddressActivity.this,AddReceiverAddressActivity.class));
                break;

        }
    }



}