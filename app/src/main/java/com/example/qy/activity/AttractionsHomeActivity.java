package com.example.qy.activity;

import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.qy.R;
import com.example.qy.adapter.RecommendedAttractionsAdapter;
import com.example.qy.ui.ChooseDestinationDialog;
import com.example.qy.whs.BaseActivity;

public class AttractionsHomeActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rc_recommended_attractions;
    private NestedScrollView nsv_recommended_attractions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attractions_home);

        findViewById(R.id.iv_back).setOnClickListener(this);
        findViewById(R.id.civ_top).setOnClickListener(this);
        findViewById(R.id.li_choose_destination).setOnClickListener(this);

        rc_recommended_attractions = findViewById(R.id.rc_recommended_attractions);
        rc_recommended_attractions.setLayoutManager(new LinearLayoutManager(this));
        rc_recommended_attractions.setAdapter(new RecommendedAttractionsAdapter(this));

        rc_recommended_attractions.setNestedScrollingEnabled(false);
        nsv_recommended_attractions = findViewById(R.id.nsv_recommended_attractions);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.civ_top:
                nsv_recommended_attractions.smoothScrollTo(0,0);
                break;
            case R.id.li_choose_destination:
                ChooseDestinationDialog dialog = new ChooseDestinationDialog(this);
                dialog.show();
                break;
        }
    }
}
