package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class IntegralMallActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_lucky_draw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_mall);

        init("积分商城");

        iv_lucky_draw = findViewById(R.id.iv_lucky_draw);
        iv_lucky_draw.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_lucky_draw:
                startActivity(new Intent(this,IntegralDrawActivity.class));
                break;
        }
    }
}
