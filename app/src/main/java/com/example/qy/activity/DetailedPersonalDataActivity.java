package com.example.qy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class DetailedPersonalDataActivity extends BaseActivity {
    private TextView tv_icon_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_personal_data);

        tv_icon_data = findViewById(R.id.tv_icon_data);
        tv_icon_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailedPersonalDataActivity.this,UploadIconActivity.class));
            }
        });
    }
}
