package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.qy.R;
import com.example.qy.adapter.FollowersAdapter;
import com.example.qy.bean.Follwers;
import com.example.qy.bean.UserInfo;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FocusActivity extends AppCompatActivity {
    private RecyclerView rl_focus;
    private List<Follwers> lists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);

        UserInfo userInfo = ((MyApplication)getApplication()).getUserInfo();
        final int id = userInfo.loginId;
        int type = getIntent().getIntExtra("type",0);
        rl_focus = findViewById(R.id.rl_focus);
        lists = new ArrayList<>();

        if (type == 1){
            // 关注
            HttpUtils.sendOkHttpRequest(HttpQYUtils.getShowAttention(id, 0), new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(()->{
                        ToastUtils.showShort(FocusActivity.this,"网络中断");
                    });
                }
                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    try {
                        JSONObject object = new JSONObject(responseText);
                        if (object.getBoolean("isSuc")){
                            JSONObject data = object.getJSONObject("data");
                            JSONArray array = data.getJSONArray("followerList");
                            for (int i = 0;i<array.length();i++){
                                JSONObject userInfo = array.getJSONObject(i).getJSONObject("userInfo");
                                Follwers follwers = new Follwers();
                                follwers.together = array.getJSONObject(i).getString("together");
                                follwers.name = userInfo.getString("infoNickname");
                                follwers.icon = userInfo.getString("infoIcon");
                                follwers.signature = userInfo.getString("infoSignature");
                                lists.add(follwers);
                            }
                            runOnUiThread(()->{
                                LinearLayoutManager manager = new LinearLayoutManager(FocusActivity.this);
                                FollowersAdapter adapter = new FollowersAdapter(FocusActivity.this,lists);
                                rl_focus.setLayoutManager(manager);
                                rl_focus.setAdapter(adapter);
                            });
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else if(type == 2){
            // 粉丝

        }


//        Follwers follwers = new Follwers();
//        follwers.setIcon("https://pic.qqtn.com/up/2019-2/2019020508422893964.jpg");
//        follwers.setName("用户1");
//        follwers.setWorkAndFans("作品125 粉丝546");
//        lists.add(follwers);

    }
}
