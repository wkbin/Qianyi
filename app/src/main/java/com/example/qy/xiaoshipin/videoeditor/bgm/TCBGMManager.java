package com.example.qy.xiaoshipin.videoeditor.bgm;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.qy.whs.MyApplication;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.liteav.basic.log.TXCLog;
import com.example.qy.xiaoshipin.videoeditor.bgm.utils.TCBGMDownloadProgress;
import com.example.qy.xiaoshipin.videoeditor.bgm.utils.TCBGMInfo;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by vinsonswang on 2017/12/8.
 */

public class TCBGMManager {
    private static final String TAG = "TCBgmManager";
    private boolean isLoading;
    private SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getApplication());
    private LoadBgmListener mLoadBgmListener;

    private static class TCBgmMgrHolder {
        private static TCBGMManager instance = new TCBGMManager();
    }

    public static TCBGMManager getInstance() {
        return TCBgmMgrHolder.instance;
    }

    public void loadBgmList(){
        if(isLoading){
            TXCLog.e(TAG, "loadBgmList, is loading");
            return;
        }
        isLoading = true;
//        TCHttpEngine.getInstance().get(TCConstants.SVR_BGM_GET_URL, new TCHttpEngine.Listener() {
//            @Override
//            public void onResponse(int retCode, String retMsg, JSONObject retData) {
//                TXCLog.i(TAG, "retData = " + retData);
//                try {
//                    JSONObject bgmObject = retData.getJSONObject("bgm");
//                    if(bgmObject == null && mLoadBgmListener != null){
//                        mLoadBgmListener.onBgmList(null);
//                        return;
//                    }
//                    JSONArray list = bgmObject.getJSONArray("list");
//                    Type listType = new TypeToken<ArrayList<TCBGMInfo>>(){}.getType();
//                    ArrayList<TCBGMInfo> bgmInfoList = new Gson().fromJson(list.toString(), listType);
//
//                    getLocalPath(bgmInfoList);
//                    if(mLoadBgmListener != null){
//                        mLoadBgmListener.onBgmList(bgmInfoList);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                } finally {
//                    isLoading = false;
//                }
//            }
//        });
    }

    /**
     * 根据bgmList，获取本地已保存过的路径
     * @param bgmInfoList
     */
    private void getLocalPath(ArrayList<TCBGMInfo> bgmInfoList){
        if(bgmInfoList == null || bgmInfoList.size() == 0){
            return;
        }
        for(TCBGMInfo tcbgmInfo : bgmInfoList){
            tcbgmInfo.localPath = mPrefs.getString(tcbgmInfo.name, "");
        }
        for(TCBGMInfo tcbgmInfo : bgmInfoList){
            if(!tcbgmInfo.localPath.equals("")){
                tcbgmInfo.status = TCBGMInfo.STATE_DOWNLOADED;
            }
        }
    }

    public void downloadBgmInfo(final String bgmName, final int position, String url){
        Log.d("SelectBGMActivity","url == "+url);
        TCBGMDownloadProgress tcbgmDownloadProgress = new TCBGMDownloadProgress(bgmName, position, url);
        tcbgmDownloadProgress.start(new TCBGMDownloadProgress.Downloadlistener() {
            @Override
            public void onDownloadFail(String errorMsg) {
                LoadBgmListener loadBgmListener = null;
                synchronized (TCBGMManager.this){
                    loadBgmListener = mLoadBgmListener;
                }

                if(loadBgmListener != null){
                    loadBgmListener.onDownloadFail(position, errorMsg);
                }
            }

            @Override
            public void onDownloadProgress(int progress) {
                TXCLog.i(TAG, "downloadBgmInfo, progress = " + progress);
                LoadBgmListener loadBgmListener = null;
                synchronized (TCBGMManager.this){
                    loadBgmListener = mLoadBgmListener;
                }
                if(loadBgmListener != null){
                    loadBgmListener.onDownloadProgress(position, progress);
                }
            }

            @Override
            public void onDownloadSuccess(String filePath) {
                TXCLog.i(TAG, "onDownloadSuccess, filePath = " + filePath);
                LoadBgmListener loadBgmListener = null;
                synchronized (TCBGMManager.this){
                    loadBgmListener = mLoadBgmListener;
                }
                if(loadBgmListener != null){
                    loadBgmListener.onBgmDownloadSuccess(position, filePath);
                }
                // 本地保存，防止重复下载
                synchronized (TCBGMManager.this) {
                    mPrefs.edit().putString(bgmName, filePath).apply();
                }
            }
        });

    }

    public void setOnLoadBgmListener(LoadBgmListener loadBgmListener){
        synchronized (this) {
            mLoadBgmListener = loadBgmListener;
        }
    }

    public interface LoadBgmListener {

        void onBgmList(final ArrayList<TCBGMInfo> tcBgmInfoList);

        void onBgmDownloadSuccess(int position, String filePath);

        void onDownloadFail(int position, String errorMsg);

        void onDownloadProgress(int position, int progress);
    }
}
