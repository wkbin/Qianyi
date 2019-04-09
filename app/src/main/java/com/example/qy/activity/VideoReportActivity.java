package com.example.qy.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class VideoReportActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_report);

        init("视频举报");

        findViewById(R.id.rl_theft_works).setOnClickListener(this);
        findViewById(R.id.rl_porn).setOnClickListener(this);
        findViewById(R.id.rl_political).setOnClickListener(this);
        findViewById(R.id.rl_Illegal_crime).setOnClickListener(this);
        findViewById(R.id.rl_spam).setOnClickListener(this);
        findViewById(R.id.rl_rumor).setOnClickListener(this);
        findViewById(R.id.rl_abuse).setOnClickListener(this);
        findViewById(R.id.rl_minor).setOnClickListener(this);
        findViewById(R.id.rl_bad_content).setOnClickListener(this);
        findViewById(R.id.rl_an_uncomfortable).setOnClickListener(this);
        findViewById(R.id.rl_self_harm).setOnClickListener(this);
        findViewById(R.id.rl_lie_to_praise).setOnClickListener(this);
        findViewById(R.id.rl_other).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,VideoReportActivity2.class);
        switch (v.getId()){
            case R.id.rl_theft_works:
                intent.putExtra("type_id",-1);
                intent.putExtra("type","盗用TA人作品");
                break;
            case R.id.rl_porn:
                intent.putExtra("type","色情低俗");
                break;
            case R.id.rl_political:
                intent.putExtra("type","政治敏感");
                break;
            case R.id.rl_Illegal_crime:
                intent.putExtra("type","违法犯罪");
                break;
            case R.id.rl_spam:
                intent.putExtra("type","垃圾广告、售卖假货等");
                break;
            case R.id.rl_rumor:
                intent.putExtra("type","造谣传谣、涉嫌欺诈");
                break;
            case R.id.rl_abuse:
                intent.putExtra("type","侮辱谩骂");
                break;
            case R.id.rl_minor:
                intent.putExtra("type","未成年人不当行为");
                break;
            case R.id.rl_bad_content:
                intent.putExtra("type","内容不适合未成年观看");
                break;
            case R.id.rl_an_uncomfortable:
                intent.putExtra("type","引人不适");
                break;
            case R.id.rl_self_harm:
                intent.putExtra("type","疑似自我伤害");
                break;
            case R.id.rl_lie_to_praise:
                intent.putExtra("type","诱导点赞、分享、关注");
                break;
            case R.id.rl_other:
                intent.putExtra("type","其他");
                break;
        }
        startActivity(intent);
    }
}
