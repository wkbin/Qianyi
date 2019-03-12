package com.example.qy.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FocusActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView rl_focus;
    private List<Follwers> lists;
    private TextView action_bar_text;
    private List<String> listIds;
    private FollowersAdapter adapter;
    private ImageView action_bar_iv_left;
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus);


        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        action_bar_iv_left.setOnClickListener(this);



        UserInfo userInfo = ((MyApplication)getApplication()).getUserInfo();
        final int id = userInfo.loginId;
        type = getIntent().getIntExtra("type",0);
        rl_focus = findViewById(R.id.rl_focus);
        lists = new ArrayList<>();

        String url = "";
        if (type == 1){
            action_bar_text.setText("我的关注");
            // 关注
            url = HttpQYUtils.getShowAttention(id, 0);

        }else if(type == 2){
            // 粉丝
            action_bar_text.setText("我的粉丝");
            url = HttpQYUtils.getFindFansDetails(id,0);
        }

        HttpUtils.sendOkHttpRequest(url, new Callback() {
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
                        JSONArray array = null;
                        if (type == 1){
                            array = data.getJSONArray("followerList");
                        }else if(type == 2){
                            array = data.getJSONArray("fansDetails");
                        }

                        for (int i = 0;i<array.length();i++){
                            JSONObject userInfo = array.getJSONObject(i).getJSONObject("userInfo");
                            Follwers follwers = new Follwers();
                            follwers.together = array.getJSONObject(i).getString("together");
                            follwers.name = userInfo.getString("infoNickname");
                            follwers.icon = userInfo.getString("infoIcon");
                            follwers.signature = userInfo.getString("infoSignature");
                            follwers.loginId = userInfo.getString("loginId");
                            lists.add(follwers);
                        }
                        runOnUiThread(()->{
                            LinearLayoutManager manager = new LinearLayoutManager(FocusActivity.this);
                            adapter = new FollowersAdapter(FocusActivity.this,lists,type);
                            rl_focus.setLayoutManager(manager);
                            rl_focus.setAdapter(adapter);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        super.onKeyDown(keyCode, event);


        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Do something.
            if(type == 1){
                if (lists.size() == 0) return true;
                Map<String,Boolean> map = adapter.getMap();
                StringBuilder stringBuilder = new StringBuilder();
                for (Follwers follwers:lists){
                    if (map.containsKey(follwers.loginId) && map.get(follwers.loginId) != null && map.get(follwers.loginId)){
                        stringBuilder.append(follwers.loginId).append(",");
                    }
                }

                int userId = ((MyApplication)getApplication()).getUserInfo().loginId;
                HttpUtils.sendOkHttpRequest(HttpQYUtils.getOffAttention(userId, stringBuilder.toString()), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(()->{
                            ToastUtils.showShort(FocusActivity.this,"网络中断");
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                    }
                });
                Log.d("666","listId = "+stringBuilder.toString());
            }else if(type == 2){
                if (lists.size() == 0) return true;
                Map<String,Boolean> map = adapter.getMap();
                StringBuilder stringBuilder = new StringBuilder();
                for (Follwers follwers:lists){
                    if (map.containsKey(follwers.loginId) && map.get(follwers.loginId) != null && map.get(follwers.loginId)){
                        stringBuilder.append(follwers.loginId).append(",");
                    }
                }
                Log.d("666","listId = "+stringBuilder.toString());
            }

            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_bar_iv_left:
                finish();
                break;
        }
    }
}
