package com.example.qy.activity;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.qy.R;
import com.example.qy.adapter.IntegralAdapter;
import com.example.qy.fragment.LuckyDrawAdapter;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;

public class IntegralDrawActivity extends BaseActivity {
    // 九宫格抽奖图片
    private int[] images = {
            R.mipmap.fortypoints,R.mipmap.iphone,R.mipmap.fortypoints,
            R.mipmap.thank, Color.TRANSPARENT,R.mipmap.thank,
            R.mipmap.specialtybox,R.mipmap.thirtypoint,R.mipmap.ticket
    };
    private RecyclerView rc_lucky_draw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral_draw);

        init("积分抽奖");

        rc_lucky_draw = findViewById(R.id.rc_lucky_draw);
        GridLayoutManager manager = new GridLayoutManager(this,3);
        rc_lucky_draw.setLayoutManager(manager);

        LuckyDrawAdapter adapter = new LuckyDrawAdapter(this,images);
        rc_lucky_draw.setAdapter(adapter);


    }
}
