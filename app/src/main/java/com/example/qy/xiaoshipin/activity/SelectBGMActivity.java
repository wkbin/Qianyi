package com.example.qy.xiaoshipin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qy.R;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.example.qy.xiaoshipin.adapter.BGMAdapter;
import com.example.qy.xiaoshipin.bean.Music;
import com.example.qy.xiaoshipin.common.utils.TCConstants;
import com.example.qy.xiaoshipin.videoeditor.bgm.BGMSelectActivity;
import com.example.qy.xiaoshipin.videoeditor.bgm.TCBGMManager;
import com.example.qy.xiaoshipin.videoeditor.bgm.TCMusicAdapter;
import com.example.qy.xiaoshipin.videoeditor.bgm.utils.TCBGMInfo;
import com.example.qy.xiaoshipin.videoeditor.common.widget.progress.SampleProgressButton;
import com.tencent.liteav.basic.log.TXCLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class SelectBGMActivity extends BaseActivity implements View.OnClickListener {
    private ImageView iv_qingchu;
    private EditText et_music_name;
    private RecyclerView rc_choose_music;
    private List<Music> musicList;
    private TCBGMManager.LoadBgmListener mLoadBgmListener;
    private BGMAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_bgm);

        startLoading();
        iv_qingchu = findViewById(R.id.iv_qingchu);
        iv_qingchu.setOnClickListener(this);
        et_music_name = findViewById(R.id.et_music_name);
        findViewById(R.id.iv_back).setOnClickListener(this);

        et_music_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    iv_qingchu.setVisibility(View.VISIBLE);
                } else {
                    iv_qingchu.setVisibility(View.GONE);
                }
            }
        });

        musicList = new ArrayList<>();
        rc_choose_music = findViewById(R.id.rc_choose_music);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        manager.setOrientation(GridLayoutManager.HORIZONTAL);
        rc_choose_music.setLayoutManager(manager);
        new LinearSnapHelper().attachToRecyclerView(rc_choose_music);

        HttpUtils.sendOkHttpRequest(HttpQYUtils.getSelectAllMusic(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    stopLoading();
                    ToastUtils.showShort(SelectBGMActivity.this, "网络断开");
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseText);
                    String msg = jsonObject.getString("msg");
                    if (jsonObject.getBoolean("isSuc")) {
                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        JSONArray recommendList = dataObject.getJSONArray("recommendList");
                        for (int i = 0; i < recommendList.length(); i++) {
                            JSONObject recommend = recommendList.getJSONObject(i);
                            Music music = new Music();
                            String musicName = recommend.getString("musicName");
                            String musicPath = recommend.getString("musicPath");
                            String coverPath = recommend.getString("coverPath");
                            String creatorName = recommend.getString("creatorname");
                            music.musicName = musicName;
                            music.musicPath = musicPath;
                            music.creatorName = creatorName;
                            music.musicImage = coverPath;
                            musicList.add(music);
                        }
                        runOnUiThread(() -> {
                            stopLoading();
                            adapter = new BGMAdapter(SelectBGMActivity.this, musicList);
                            adapter.setOnClickSubItemListener(new BGMAdapter.OnClickSubItemListener() {
                                @Override
                                public void onClickUseBtn(SampleProgressButton button, int position) {
                                    Music music = musicList.get(position);
                                    Log.d("SelectBGMActivity","music == "+music.toString());
                                    if (music.status == Music.STATE_UNDOWNLOAD) {
                                        music.status = Music.STATE_DOWNLOADING;
                                        adapter.updateItem(position, music);
                                        downloadMusic(position);
                                    } else if (music.status == Music.STATE_DOWNLOADED) {
                                        backToEditActivity(position, music.musicPath);
                                    }
                                }
                            });
                            rc_choose_music.setAdapter(adapter);


                            mLoadBgmListener = new TCBGMManager.LoadBgmListener() {
                                @Override
                                public void onBgmList(final ArrayList<TCBGMInfo> tcBgmInfoList) {

                                }

                                @Override
                                public void onBgmDownloadSuccess(final int position, final String filePath) {

                                    try {
                                        Thread.sleep(200);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Music music = musicList.get(position);
                                            music.status = TCBGMInfo.STATE_DOWNLOADED;
                                            music.musicPath = filePath;
                                            adapter.updateItem(position, music);

                                            Toast.makeText(SelectBGMActivity.this
                                                    , "下载成功"
                                                    , Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onDownloadFail(final int position, final String errorMsg) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Music music = musicList.get(position);
                                            music.status = TCBGMInfo.STATE_UNDOWNLOAD;
                                            music.progress = 0;
                                            adapter.updateItem(position, music);
                                            Toast.makeText(SelectBGMActivity.this
                                                    , getResources().getString(R.string.bgm_select_activity_download_failed)
                                                    , Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                                @Override
                                public void onDownloadProgress(final int position, final int progress) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d("SelectBGMActivity", "下载进度 == " + progress);
                                            Music music = musicList.get(position);
                                            music.status = TCBGMInfo.STATE_DOWNLOADING;
                                            music.progress = progress;
                                            adapter.updateItem(position, music);
                                        }
                                    });

                                }
                            };
                            TCBGMManager.getInstance().setOnLoadBgmListener(mLoadBgmListener);
                        });
                    } else {
                        runOnUiThread(() -> {
                            stopLoading();
                            ToastUtils.showShort(SelectBGMActivity.this, msg);
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void downloadMusic(int position) {
        Music music = musicList.get(position);
        Log.d("SelectBGMActivity","music2 == "+music.toString());
        if (TextUtils.isEmpty(music.musicPath)) {
            downloadBgmInfo(position, music);
            music.status = TCBGMInfo.STATE_DOWNLOADING;
            music.progress = 0;
            adapter.updateItem(position, music);
            return;
        }
        File file = new File(music.musicPath);
        if (!file.isFile()) {
//            music.musicPath = "";
            music.status = TCBGMInfo.STATE_DOWNLOADING;
            music.progress = 0;
            adapter.updateItem(position, music);
            downloadBgmInfo(position, music);
            return;
        }
    }

    private void backToEditActivity(int position, String path) {
        TCBGMManager.getInstance().setOnLoadBgmListener(null);
        Intent intent = new Intent();
        intent.putExtra(TCConstants.BGM_POSITION, position);
        intent.putExtra(TCConstants.BGM_PATH, path);
        intent.putExtra(TCConstants.BGM_NAME, musicList.get(position).musicName);
        setResult(TCConstants.ACTIVITY_BGM_REQUEST_CODE, intent);
        finish();
    }

    private void downloadBgmInfo(int position, Music music) {
        Log.d("SelectBGMActivity","music3 == "+music.toString());
        TCBGMManager.getInstance().downloadBgmInfo(music.musicName, position, music.musicPath);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_qingchu:
                et_music_name.setText("");
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
