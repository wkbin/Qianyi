package com.example.qy.xiaoshipin.videoeditor.bgm.utils;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;


import com.example.qy.R;
import com.example.qy.whs.MyApplication;
import com.example.qy.xiaoshipin.common.widget.beautysetting.utils.HttpFileListener;
import com.example.qy.xiaoshipin.common.widget.beautysetting.utils.HttpFileUtil;
import com.example.qy.xiaoshipin.common.widget.beautysetting.utils.VideoDeviceUtil;

import java.io.File;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by kevinxing on 2016/8/5.
 */
public class TCBGMDownloadProgress {
//    public static final String DOWNLOAD_FILE_POSTFIX = ".zip";
    public static final String BGM_FOLDER = "bgm";
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = 8;
    private boolean mProcessing;

    private String mUrl;
    private Downloadlistener mListener;
    private DownloadThreadPool sDownloadThreadPool;
    private String mBgmName;
    private int mBgmPosition;

    public TCBGMDownloadProgress(String bgmName, int position, String url){
        this.mBgmName = bgmName;
        this.mBgmPosition = position;
        this.mUrl = url;
        mProcessing = false;
    }
    public void start(Downloadlistener listener) {
        if(listener == null || TextUtils.isEmpty(mUrl) || mProcessing){
            return;
        }
        this.mListener = listener;
        mProcessing = true;
        mListener.onDownloadProgress(0);
        HttpFileListener fileListener = new HttpFileListener() {
            @Override
            public void onSaveSuccess(File file) {
                mListener.onDownloadSuccess(file.getPath());
                stop();
            }

            @Override
            public void onSaveFailed(File file, Exception e) {
                mListener.onDownloadFail(e.getMessage());
                stop();
            }

            @Override
            public void onProgressUpdate(int progress) {
                mListener.onDownloadProgress(progress);
            }

            @Override
            public void onProcessEnd() {
                mProcessing = false;
            }

        };
        File onlineMaterialDir = VideoDeviceUtil.getExternalFilesDir(MyApplication.getApplication(), BGM_FOLDER);
        if (onlineMaterialDir == null || onlineMaterialDir.getName().startsWith("null")) {
            mListener.onDownloadFail(MyApplication.getApplication().getResources().getString(R.string.tc_bgm_download_progress_no_enough_storage_space));
            stop();
            return;
        }
        if (!onlineMaterialDir.exists()) {
            onlineMaterialDir.mkdirs();
        }

        ThreadPoolExecutor threadPool = getThreadExecutor();
        HttpFileUtil httpFileUtil = new HttpFileUtil(mUrl, onlineMaterialDir.getPath(), mBgmName, fileListener, true);
        threadPool.execute(httpFileUtil);
    }

    public void stop() {
        mListener = null;
    }

    public synchronized ThreadPoolExecutor getThreadExecutor() {
        if (sDownloadThreadPool == null || sDownloadThreadPool.isShutdown()) {
            sDownloadThreadPool = new DownloadThreadPool(CORE_POOL_SIZE);
        }
        return sDownloadThreadPool;
    }

    public static class DownloadThreadPool extends ThreadPoolExecutor {

        @TargetApi(Build.VERSION_CODES.GINGERBREAD)
        public DownloadThreadPool(int poolSize) {
            super(poolSize, poolSize, 0L, TimeUnit.MILLISECONDS,
                    new LinkedBlockingDeque<Runnable>(),
//                    Utils.hasGingerbread() ? new LinkedBlockingDeque<Runnable>() : new LinkedBlockingQueue<Runnable>(),
                    Executors.defaultThreadFactory(), new AbortPolicy());
        }
    }

    public interface Downloadlistener{
        void onDownloadFail(String errorMsg);
        void onDownloadProgress(final int progress);
        void onDownloadSuccess(String filePath);
    }
}
