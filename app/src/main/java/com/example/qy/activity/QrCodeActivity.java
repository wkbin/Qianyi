package com.example.qy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.qy.R;
import com.example.qy.bean.UserInfo;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.bean.ZxingConfig;
import com.yzq.zxinglibrary.common.Constant;
import com.yzq.zxinglibrary.encode.CodeCreator;

import de.hdodenhof.circleimageview.CircleImageView;

public class QrCodeActivity extends BaseActivity {
    private ImageView iv_qr_code;
    private LinearLayout li_scan;
    private ImageView action_bar_iv_left;
    private TextView tv_qy_code;
    private TextView tv_name;
    private CircleImageView civ_icon;

    private final int REQUEST_CODE_SCAN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);




        iv_qr_code = findViewById(R.id.iv_qr_code);
        li_scan = findViewById(R.id.li_scan);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        tv_qy_code = findViewById(R.id.tv_qy_code);
        tv_name = findViewById(R.id.tv_name);
        civ_icon = findViewById(R.id.civ_icon);
        action_bar_iv_left.setOnClickListener(v-> {
           finish();
        });

        MyApplication application = (MyApplication) getApplication();
        UserInfo userInfo = application.getUserInfo();
        Glide.with(QrCodeActivity.this).load(userInfo.icon).into(civ_icon);
        tv_qy_code.setText(userInfo.qianyiID);
        tv_name.setText(userInfo.nickname);
        /*
         * contentEtString：字符串内容
         * w：图片的宽
         * h：图片的高
         * logo：不需要logo的话直接传null
         * */

        Bitmap bitmap = CodeCreator.createQRCode(userInfo.phone, 255, 255, null);
        iv_qr_code.setImageBitmap(bitmap);

        li_scan.setOnClickListener(v -> {
            Intent intent = new Intent(QrCodeActivity.this, CaptureActivity.class);
            /*ZxingConfig是配置类
             *可以设置是否显示底部布局，闪光灯，相册，
             * 是否播放提示音  震动
             * 设置扫描框颜色等
             * 也可以不传这个参数
             * */
            ZxingConfig config = new ZxingConfig();
            config.setPlayBeep(true);//是否播放扫描声音 默认为true
            config.setShake(true);//是否震动  默认为true
            config.setDecodeBarCode(true);//是否扫描条形码 默认为true
            config.setReactColor(R.color.colorAccent);//设置扫描框四个角的颜色 默认为白色
            config.setFrameLineColor(R.color.colorAccent);//设置扫描框边框颜色 默认无色
            config.setScanLineColor(R.color.colorAccent);//设置扫描线的颜色 默认白色
            config.setFullScreenScan(false);//是否全屏扫描  默认为true  设为false则只会在扫描框中扫描
            intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
            startActivityForResult(intent, REQUEST_CODE_SCAN);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {
                String content = data.getStringExtra(Constant.CODED_CONTENT);
                ToastUtils.showShort(QrCodeActivity.this,content);
                Log.d("QrCodeActivity","扫描结果为：" + content);
            }
        }
    }
}
