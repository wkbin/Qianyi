package com.example.qy.xiaoshipin.bean;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 12 日 上午 9:13
 * Description:
 */
public class Music {
    public String musicPath;
    public String musicImage;
    public String musicName;
    public String creatorName;

    @Override
    public String toString() {
        return "Music{" +
                "musicPath='" + musicPath + '\'' +
                ", musicImage='" + musicImage + '\'' +
                ", musicName='" + musicName + '\'' +
                ", creatorName='" + creatorName + '\'' +
                '}';
    }

    public int status = STATE_UNDOWNLOAD;
    public int progress;

    public static final int STATE_UNDOWNLOAD = 1;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_DOWNLOADED = 3;
    public static final int STATE_USED = 4;
}
