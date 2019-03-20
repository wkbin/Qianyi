package com.example.qy.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.utils.ToastUtils;

/**
 * Author: 王克斌
 * Date: 2019 年 03 月 18 日 下午 4:40
 * Description:
 */
public class AddressLabelsDialog extends Dialog implements View.OnClickListener {
    public interface onClickListener{
        void onAddressClick(String str);
    }
    private onClickListener onClickListener;

    public void setOnClickListener(AddressLabelsDialog.onClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    private int state = 0;
    private RelativeLayout rl_my_self,rl_home,rl_company,rl_school;
    private ImageView iv_my_self,iv_home,iv_company,iv_school;
    private EditText et_self_content;
    private TextView tv_confirm,tv_quit;

    public AddressLabelsDialog(Context context) {
        super(context);
    }

    public AddressLabelsDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AddressLabelsDialog(Context context, boolean cancelable,DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_address_labels);

        rl_my_self = findViewById(R.id.rl_my_self);
        rl_home = findViewById(R.id.rl_home);
        rl_company = findViewById(R.id.rl_company);
        rl_school = findViewById(R.id.rl_school);
        tv_confirm = findViewById(R.id.tv_confirm);
        tv_quit = findViewById(R.id.tv_quit);
        iv_my_self = findViewById(R.id.iv_my_self);
        iv_home = findViewById(R.id.iv_home);
        iv_company = findViewById(R.id.iv_company);
        iv_school = findViewById(R.id.iv_school);
        et_self_content = findViewById(R.id.et_self_content);

        rl_my_self.setOnClickListener(this);
        rl_home.setOnClickListener(this);
        rl_company.setOnClickListener(this);
        rl_school.setOnClickListener(this);
        tv_confirm.setOnClickListener(this);
        tv_quit.setOnClickListener(this);
        et_self_content.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_self_content:
            case R.id.rl_my_self:
                state = 0;
                iv_my_self.setVisibility(View.VISIBLE);
                iv_home.setVisibility(View.INVISIBLE);
                iv_company.setVisibility(View.INVISIBLE);
                iv_school.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_home:
                state = 1;
                iv_home.setVisibility(View.VISIBLE);
                iv_my_self.setVisibility(View.INVISIBLE);
                iv_company.setVisibility(View.INVISIBLE);
                iv_school.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_company:
                state = 2;
                iv_company.setVisibility(View.VISIBLE);
                iv_my_self.setVisibility(View.INVISIBLE);
                iv_home.setVisibility(View.INVISIBLE);
                iv_school.setVisibility(View.INVISIBLE);
                break;
            case R.id.rl_school:
                state = 3;
                iv_my_self.setVisibility(View.INVISIBLE);
                iv_home.setVisibility(View.INVISIBLE);
                iv_company.setVisibility(View.INVISIBLE);
                iv_school.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_confirm:
                String content = "";
                switch (state){
                    case 0:
                        content = et_self_content.getText().toString().trim();
                        break;
                    case 1:
                        content = "家";
                        break;
                    case 2:
                        content = "公司";
                        break;
                    case 3:
                        content = "学校";
                        break;
                }
                onClickListener.onAddressClick(content);
                dismiss();
                break;
            case R.id.tv_quit:
                dismiss();
                break;
        }
    }
}
