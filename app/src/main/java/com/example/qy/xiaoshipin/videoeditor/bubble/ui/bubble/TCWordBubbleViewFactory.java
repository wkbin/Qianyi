package com.example.qy.xiaoshipin.videoeditor.bubble.ui.bubble;

import android.content.Context;
import android.view.View;

import com.example.qy.R;

/**
 * Created by hanszhli on 2017/6/21.
 * <p>
 * 创建 OperationView的工厂
 */

public class TCWordBubbleViewFactory {

    public static TCWordBubbleView newOperationView(Context context) {
        return (TCWordBubbleView) View.inflate(context, R.layout.layout_default_bubble_view, null);
    }
}
