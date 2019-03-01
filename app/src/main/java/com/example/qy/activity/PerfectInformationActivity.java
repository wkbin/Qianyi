package com.example.qy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.qy.R;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;
import cn.qqtheme.framework.util.ConvertUtils;
import cn.qqtheme.framework.widget.WheelView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PerfectInformationActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv_login_title;
    private RelativeLayout rl_perfect_sex,rl_perfect_year,rl_perfect_address;
    private TextView tv_perfect_birthday;
    private TextView tv_perfect_sex;
    private TextView tv_perfect_address;
    private CityPickerView mPicker;
    private EditText et_perfect_infoManifesto;
    private Button btn_save_login;
    private Button btn_save_cancel;
    private String phone;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            if (msg.what == 0x007){
                if (bundle.getBoolean("isSuc")){
                    Intent intent = new Intent(PerfectInformationActivity.this,LoginActivity.class);
                    intent.putExtra("phone",phone);
                    startActivity(intent);
                }else{
                    ToastUtils.showShort(PerfectInformationActivity.this,bundle.getString("message"));
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_information);

        initView();

    }


    private void selectAddress() {
        //添加默认的配置，不需要自己定义
        CityConfig cityConfig = new CityConfig.Builder().build();
        cityConfig.setConfirmTextColorStr("#101010");
        mPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                //将选择的地址填入tv_address_set中
                tv_perfect_address.setText(province.toString().trim() + city.toString().trim() + district.toString().trim());
            }
            @Override
            public void onCancel() {
            }
        });

        //显示
        mPicker.showCityPicker();

    }

    private void initView(){

        phone = getIntent().getStringExtra("phone");

        tv_login_title = findViewById(R.id.tv_login_title);
        tv_login_title.setText("完善资料");

        rl_perfect_sex = findViewById(R.id.rl_perfect_sex);
        rl_perfect_year = findViewById(R.id.rl_perfect_year);
        rl_perfect_address = findViewById(R.id.rl_perfect_address);
        tv_perfect_address = findViewById(R.id.tv_perfect_address);
        et_perfect_infoManifesto = findViewById(R.id.et_perfect_infoManifesto);

        btn_save_login = findViewById(R.id.btn_save_login);
        btn_save_cancel = findViewById(R.id.btn_save_cancel);

        mPicker =new CityPickerView();
        mPicker.init(this);

        tv_perfect_birthday = findViewById(R.id.tv_perfect_birthday);
        tv_perfect_sex = findViewById(R.id.tv_perfect_sex);
        rl_perfect_sex.setOnClickListener(this);
        rl_perfect_year.setOnClickListener(this);
        rl_perfect_address.setOnClickListener(this);
        btn_save_login.setOnClickListener(this);
        btn_save_cancel.setOnClickListener(this);

    }
    private void onYearMonthDayPicker() {
        final DatePicker picker = new DatePicker(this);
        picker.setCanceledOnTouchOutside(true);
        picker.setUseWeight(true);
        picker.setTopPadding(ConvertUtils.toPx(this, 20));
        picker.setRangeEnd(2050, 10, 14);//控件最大所能显示的时间，即结束时间
        picker.setRangeStart(1970, 1, 1);//控件最小所能显示的时间
        if (!TextUtils.isEmpty(tv_perfect_birthday.getText().toString().trim())){
            String str[] = tv_perfect_birthday.getText().toString().trim().split("-");
            String year = str[0];
            String month = str[1];
            String day = str[2];
            picker.setSelectedItem(Integer.parseInt(year),Integer.parseInt(month),Integer.parseInt(day));

        }

        picker.setCancelText("取消");
        picker.setSubmitText("确定");
        picker.setOnDatePickListener(new DatePicker.OnYearMonthDayPickListener() {
            @Override
            public void onDatePicked(String year, String month, String day) {
                tv_perfect_birthday.setText(year + "-" + month + "-" + day);
            }
        });
        picker.setOnWheelListener(new DatePicker.OnWheelListener() {
            @Override
            public void onYearWheeled(int index, String year) {
                picker.setTitleText(year + "-" + picker.getSelectedMonth() + "-" + picker.getSelectedDay());
            }

            @Override
            public void onMonthWheeled(int index, String month) {
                picker.setTitleText(picker.getSelectedYear() + "-" + month + "-" + picker.getSelectedDay());
            }

            @Override
            public void onDayWheeled(int index, String day) {
                picker.setTitleText(picker.getSelectedYear() + "-" + picker.getSelectedMonth() + "-" + day);
            }
        });
        picker.show();
    }
    private void onOptionPicker() {
        OptionPicker picker = new OptionPicker(this, new String[]{
                "男", "女"
        });
        picker.setCanceledOnTouchOutside(false);
        picker.setCancelText("取消");
        picker.setSubmitText("确定");
        picker.setDividerRatio(WheelView.DividerConfig.FILL);
        picker.setTextSize(16);
        if (tv_perfect_sex.getText().toString().trim().equals("男")){
            picker.setSelectedIndex(0);
        }else{
            picker.setSelectedIndex(1);
        }

        picker.setCycleDisable(true);
        picker.setOnOptionPickListener(new OptionPicker.OnOptionPickListener() {
            @Override
            public void onOptionPicked(int index, String item) {
                tv_perfect_sex.setText(item);
            }
        });
        picker.show();
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_perfect_sex:
                // 单项选择器
                onOptionPicker();
                break;
            case R.id.rl_perfect_year:
                // 日期选择器
                onYearMonthDayPicker();
                break;
            case R.id.rl_perfect_address:
                // 地址选择器
                selectAddress();
                break;
            case R.id.btn_save_login:
                String sex = tv_perfect_sex.getText().toString().trim();
                String birthday =  tv_perfect_birthday.getText().toString().trim();
                String infoManifesto = et_perfect_infoManifesto.getText().toString().trim();
                String address = tv_perfect_address.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    ToastUtils.showShort(PerfectInformationActivity.this,"phone is null!");
                    return;
                }
                String url = HttpQYUtils.getMaterial(phone,sex,birthday,address,infoManifesto);

                Log.d("666","url = "+url);
                HttpUtils.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseText = response.body().string();
                    try {
                        JSONObject jsonObject = new JSONObject(responseText);
                        String message = jsonObject.getString("msg");
                        boolean isSuc = jsonObject.getBoolean("isSuc");
                        Bundle bundle = new Bundle();
                        Message msg = new Message();
                        msg.what = 0x007;
                        bundle.putBoolean("isSuc",isSuc);
                        bundle.putString("message",message);
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            break;
            case R.id.btn_save_cancel:
                Intent intent = new Intent(PerfectInformationActivity.this,LoginActivity.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                break;
        }



    }
}
