package com.example.qy.activity;

import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class SpecialtyDetailsActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_original_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specialty_details);

        tv_original_price = findViewById(R.id.tv_original_price);
        tv_original_price.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG );

        findViewById(R.id.iv_back).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
