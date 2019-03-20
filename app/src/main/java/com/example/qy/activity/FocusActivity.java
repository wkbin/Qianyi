package com.example.qy.activity;

import android.os.Build;
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
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FocusActivity extends BaseActivity implements View.OnClickListener {
    private RecyclerView rl_focus;
    private List<Follwers> lists;
    private TextView action_bar_text;
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
            int userId = ((MyApplication)getApplication()).getUserInfo().loginId;
            if(type == 1){
                Log.d("888","type = 1");
                if (lists.size() == 0) return true;
                Map<String,Boolean> map = adapter.getMap();
                StringBuilder stringBuilder = new StringBuilder();
                for (Follwers follwers:lists){
                    if (map.containsKey(follwers.loginId) && map.get(follwers.loginId) != null && map.get(follwers.loginId)){
                        Log.d("154","取消关注 = "+follwers.loginId);
                        stringBuilder.append(follwers.loginId).append(",");
                    }
                }
                HttpUtils.sendOkHttpRequest(HttpQYUtils.getOffAttention(userId, stringBuilder.toString()), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {}
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {}
                });



            }else if(type == 2){
                Log.d("888","type = 2");
                if (lists.size() == 0 ) return true;
                Map<String,Map<String,Boolean>> map2 = adapter.getMap2();
                StringBuilder attentionBuilder = new StringBuilder();
                StringBuilder cancelBuilder = new StringBuilder();
                for (Follwers follwers:lists){
                    if (map2.containsKey(follwers.loginId) && map2.get(follwers.loginId) != null){
                        Map<String,Boolean> map = map2.get(follwers.loginId);
                        if (map.get("together")){
                            Log.d("154","取消关注 = "+follwers.loginId);
                            cancelBuilder.append(follwers.loginId).append(",");
                        }else{
                            Log.d("154","添加关注 = "+follwers.loginId);
                            attentionBuilder.append(follwers.loginId).append(",");
                        }
                    }
                }
//                Log.d("154","stringBuilder = "+stringBuilder.toString());

                if (attentionBuilder.toString().length() == 0 &&cancelBuilder.toString().length() == 0 ) return true;
                    Log.d("154","attentionBuilder ==" +attentionBuilder.toString());
                    HttpUtils.sendOkHttpRequest(HttpQYUtils.getAddAttention(userId,attentionBuilder.toString()), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {}

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {}
                    });



                    Log.d("666","cancelBuilder == "+cancelBuilder.toString().length());
                    HttpUtils.sendOkHttpRequest(HttpQYUtils.getOffAttention(userId,cancelBuilder.toString()), new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {}
                        @Override
                        public void onResponse(Call call, Response response) throws IOException {}
                    });
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
