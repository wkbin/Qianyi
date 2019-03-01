package com.example.qy.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast工具类
 * 能少写就少写
 */
public class ToastUtils {
    /**
     * 弹出短时间提示
     *
     * @param context 上下文
     * @param msg     提示的信息
     */
    public static void showShort(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();

    }
    /**
     * 弹出长时间提示
     *
     * @param context 上下文
     * @param msg     提示的信息
     */
    public static void showLong(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

    }
}