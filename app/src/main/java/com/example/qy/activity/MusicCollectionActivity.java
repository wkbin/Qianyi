package com.example.qy.activity;

import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.adapter.MusicAdapter;
import com.example.qy.imp.AppBarStateChangeListener;
import com.example.qy.whs.BaseActivity;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

public class MusicCollectionActivity extends BaseActivity {
    private RecyclerView rc_music;
    private AppBarLayout app_bar;
    private Toolbar toolbar;
    private ImageView iv_back2;
    private TextView tv_name2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        setContentView(R.layout.activity_music_collection);

        init("用《为爱痴狂》拍摄的视频");

        // 开启走马灯效果
        getAction_bar_text().setSelected(true);

        tv_name2 = findViewById(R.id.tv_name2);
        tv_name2.setSelected(true);

        rc_music = findViewById(R.id.rc_music);

        GridLayoutManager  manager = new GridLayoutManager(MusicCollectionActivity.this,3);
        rc_music.setLayoutManager(manager);
        rc_music.setAdapter(new MusicAdapter(this));

        app_bar = findViewById(R.id.app_bar);
        toolbar = findViewById(R.id.toolbar);
        iv_back2 = findViewById(R.id.iv_back2);
        iv_back2.setOnClickListener(v->{
            finish();
        });
        app_bar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                Log.d("STATE", state.name());
                if( state == State.EXPANDED ) {
                    //展开状态

                }else if(state == State.COLLAPSED){

                    //折叠状态
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    toolbar.setVisibility(View.VISIBLE);
                }else {

                    //中间状态
                    getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    toolbar.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
}
