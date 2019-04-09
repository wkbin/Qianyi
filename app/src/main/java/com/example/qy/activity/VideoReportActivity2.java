package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class VideoReportActivity2 extends BaseActivity {
    private EditText et_describe_reason;
    private TextView tv_count;
    private TextView tv_type;
    private LinearLayout li_author_information;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_report2);
        init("视频举报");
        li_author_information = findViewById(R.id.li_author_information);
        tv_type = findViewById(R.id.tv_type);

        int type_id = getIntent().getIntExtra("type_id",0);
        String type = getIntent().getStringExtra("type");
        tv_type.setText(type);

        if (type_id == -1){
            li_author_information.setVisibility(View.VISIBLE);
        }


        et_describe_reason = findViewById(R.id.et_describe_reason);
        tv_count = findViewById(R.id.tv_count);

        et_describe_reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_count.setText(s.length()+"");
            }
        });
    }
}
