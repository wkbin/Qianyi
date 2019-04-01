package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.Adapter;

import com.example.qy.R;
import com.example.qy.adapter.LandmarkAttractionsVideoDetailsAdapter;
import com.fashare.stack_layout.StackLayout;
import com.fashare.stack_layout.transformer.AlphaTransformer;
import com.fashare.stack_layout.transformer.AngleTransformer;
import com.fashare.stack_layout.transformer.StackPageTransformer;

public class LandmarkAttractionsVideoDetailsActivity extends AppCompatActivity {
    private StackLayout stack_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landmark_attractions_video_details);

        stack_layout = findViewById(R.id.stack_layout);
//        LandmarkAttractionsVideoDetailsAdapter adapter = new LandmarkAttractionsVideoDetailsAdapter(this);
//        stack_layout.setAdapter();


        stack_layout.addPageTransformer(
                new StackPageTransformer(),     // 堆叠
                new AlphaTransformer(),         // 渐变
                new AngleTransformer()          // 角度
        );

    }
}
