package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;

public class IntegralDescriptionActivity extends AppCompatActivity  {
    private ImageView action_bar_iv_left;
    private TextView action_bar_text;
    private TextView action_bar_iv_right;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_description);

        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_text.setText("积分说明");

        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);
        action_bar_iv_right.setVisibility(View.INVISIBLE);



        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_iv_left.setOnClickListener(v->{
            finish();
        });

    }


}
