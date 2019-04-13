package com.example.qy.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.qy.R;
import com.example.qy.adapter.ChooseDestinationAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: 王克斌
 * Date: 2019 年 04 月 09 日 下午 3:36
 * Description: 选择地址对话框
 */
public class ChooseDestinationDialog extends Dialog {
    private RecyclerView rc_choose_destination;
    private List<String> nameList;
    public ChooseDestinationDialog(Context context) {
        super(context,R.style.custom_dialog2);
    }

    public ChooseDestinationDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ChooseDestinationDialog(Context context, boolean cancelable,DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_destination);
        // 设置对话框大小
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置弹出位置
        getWindow().setGravity(Gravity.BOTTOM);



        rc_choose_destination = findViewById(R.id.rc_choose_destination);
        GridLayoutManager manager = new GridLayoutManager(getContext(),3);
        rc_choose_destination.setLayoutManager(manager);

        nameList = new ArrayList<>();
        nameList.add("九江");
        nameList.add("南昌");
        nameList.add("景德镇");
        nameList.add("萍乡");
        nameList.add("新余");
        nameList.add("鹰潭");
        nameList.add("上饶");
        nameList.add("宜春");
        nameList.add("抚州");
        nameList.add("吉安");
        nameList.add("赣州");

        ChooseDestinationAdapter adapter = new ChooseDestinationAdapter(getContext(),nameList);
        rc_choose_destination.setAdapter(adapter);
    }
}
