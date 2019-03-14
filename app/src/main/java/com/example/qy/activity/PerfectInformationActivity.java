package com.example.qy.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.contrarywind.view.WheelView;
import com.example.qy.R;
import com.example.qy.bean.CityBean;
import com.example.qy.bean.UserInfo;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.utils.UniquePsuedoUtils;
import com.example.qy.whs.BaseActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class PerfectInformationActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_birthday;
    private RelativeLayout rl_address;
    private TimePickerView pvCustomTime;
    private OptionsPickerView pvCustomOptions;
    private TextView tv_birthday;
    private TextView tv_address;
    private RelativeLayout rl_nan,rl_nv;
    private ImageView iv_nan,iv_nv;
    private TextView tv_nan,tv_nv;
    private EditText et_declaration;
    private Button btn_save_data;
    private String phone;
    private LinearLayout li_ignore;

    private List<CityBean> lists;
    private List<List<String>> cityLists;

    // 判断选中男女
    private boolean isBoy = true;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect_information);
        initView();
    }
    private void initView(){
        phone = getIntent().getStringExtra("phone");
        String phoneId = UniquePsuedoUtils.getUniquePsuedoID();
        HttpUtils.sendOkHttpRequest(HttpQYUtils.getValidationLogin(phone,phoneId), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.showShort(PerfectInformationActivity.this,"连接断开");
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                try {
                    JSONObject jsonObject = new JSONObject(responseText);
                    boolean isSuc = jsonObject.getBoolean("isSuc");
                    if (isSuc){

                        JSONObject dataObject = jsonObject.getJSONObject("data");
                        id = dataObject.getInt("loginId");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });


        rl_birthday = findViewById(R.id.rl_birthday);
        rl_address = findViewById(R.id.rl_address);
        tv_address = findViewById(R.id.tv_address);
        tv_birthday = findViewById(R.id.tv_birthday);
        rl_nan = findViewById(R.id.rl_nan);
        rl_nv = findViewById(R.id.rl_nv);
        iv_nan = findViewById(R.id.iv_nan);
        iv_nv = findViewById(R.id.iv_nv);
        tv_nan = findViewById(R.id.tv_nan);
        tv_nv = findViewById(R.id.tv_nv);
        et_declaration = findViewById(R.id.et_declaration);
        btn_save_data = findViewById(R.id.btn_save_data);
        li_ignore = findViewById(R.id.li_ignore);

        rl_birthday.setOnClickListener(this);
        rl_address.setOnClickListener(this);
        rl_nan.setOnClickListener(this);
        rl_nv.setOnClickListener(this);
        btn_save_data.setOnClickListener(this);
        li_ignore.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_nan:
                isBoy = true;
                iv_nan.setImageResource(R.mipmap.nandianji);
                tv_nan.setTextColor(Color.parseColor("#1a1a1a"));
                iv_nv.setImageResource(R.mipmap.nvweidianji);
                tv_nv.setTextColor(Color.parseColor("#999999"));
                break;
            case R.id.rl_nv:
                isBoy = false;
                iv_nan.setImageResource(R.mipmap.nanweidianji);
                tv_nan.setTextColor(Color.parseColor("#999999"));
                iv_nv.setImageResource(R.mipmap.nvdianji);
                tv_nv.setTextColor(Color.parseColor("#1a1a1a"));
                break;
            case R.id.rl_birthday:
                showBirthdayDialog();
                break;
            case R.id.rl_address:
                String url = HttpQYUtils.getAddress();
                HttpUtils.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastUtils.showShort(PerfectInformationActivity.this,"连接断开");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONArray jsonArray = new JSONArray(responseText);
                            lists = new ArrayList<>();
                            for (int i = 0 ; i < jsonArray.length() ; i++ ){
                                CityBean city = new CityBean();
                                List<String> cityList = new ArrayList<>();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                city.setProvince(jsonObject.getString("province"));
                                JSONArray listArray = jsonObject.getJSONArray("city_list");
                                for (int j = 0 ; j < listArray.length() ; j++ ){
                                    cityList.add(listArray.getString(j));
                                }
                                city.setCity_list(cityList);
                                lists.add(city);
                            }
                            runOnUiThread(() -> {
                                showAddressDialog();
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.btn_save_data:
                String sex = isBoy?"男":"女";
                String birthday = tv_birthday.getText().toString().trim();
                String address = tv_address.getText().toString().trim();
                String declaration = et_declaration.getText().toString().trim();

                HttpUtils.sendOkHttpRequest(HttpQYUtils.getMaterial(id,"" ,sex, birthday, address, declaration), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(() ->{
                                ToastUtils.showShort(PerfectInformationActivity.this,"连接断开");
                        });
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            boolean isSuc = jsonObject.getBoolean("isSuc");
                            String msg = jsonObject.getString("msg");
                            if (isSuc){
                                runOnUiThread(() -> {
                                    ToastUtils.showShort(PerfectInformationActivity.this,msg);
                                    Intent intent = new Intent(PerfectInformationActivity.this,MainActivity.class);
                                    intent.putExtra("phone",phone);
                                    startActivity(intent);
                                });
                            }else{
                                ToastUtils.showShort(PerfectInformationActivity.this,msg);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                String str = "sex = "+sex+" ,birthday = "+birthday+" address = "+address+", declaration = "+declaration;
                Log.d("666",str);
                break;
            case R.id.li_ignore:
                startActivity(new Intent(PerfectInformationActivity.this,MainActivity.class));
                break;
        }
    }



    private void showAddressDialog(){
        //条件选择器 ，自定义布局
        pvCustomOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                tv_address.setText(lists.get(options1).getPickerViewText() + " - " +cityLists.get(options1).get(options2));
            }
        }).setLayoutRes(R.layout.pickerview_custom_address,v -> {

                Button btn_address_finish = v.findViewById(R.id.btn_address_finish);
                TextView tv_address_cancel = v.findViewById(R.id.tv_address_cancel);
                btn_address_finish.setOnClickListener(View -> {
                        pvCustomOptions.returnData();
                        pvCustomOptions.dismiss();
                });
                tv_address_cancel.setOnClickListener(View -> {
                        pvCustomOptions.dismiss();

                });
            }
        )
                .setLineSpacingMultiplier(2f)
                .setDividerType(WheelView.DividerType.WRAP)
                .setDividerColor(Color.parseColor("#3db2a3"))//设置分割线的颜色
                .isCenterLabel(false)
                .isDialog(true)
                .build();

        cityLists = new ArrayList<>();
        for (int i=0;i < lists.size();i++) {//遍历省份
            List<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            for (int c = 0; c < lists.get(i).getCity_list().size(); c++) {//遍历该省份的所有城市
                String CityName = lists.get(i).getCity_list().get(c);
                CityList.add(CityName);//添加城市
            }
            cityLists.add(CityList);
        }
        pvCustomOptions.setPicker(lists,cityLists);
        pvCustomOptions.show();
    }
    private void showBirthdayDialog(){
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1970, 1,1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2019, 3,1);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, (date,v) -> {
                tv_birthday.setText(getTime(date));
        })
                .setRangDate(startDate,endDate)
                .setDate(selectedDate)
                .setLayoutRes(R.layout.pickerview_custom_birthday,v -> {

                        Button btn_finish = v.findViewById(R.id.btn_finish);
                        TextView tv_cancel = v.findViewById(R.id.tv_cancel);
                        btn_finish.setOnClickListener(View -> {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                        });
                        tv_cancel.setOnClickListener(View -> {
                                pvCustomTime.dismiss();
                        });
                    }
                )
                .setLineSpacingMultiplier(2f)
                .setDividerType(WheelView.DividerType.WRAP)
                .setDividerColor(Color.parseColor("#3db2a3"))//设置分割线的颜色
                .isCenterLabel(false)
                .isDialog(true)
                .setType(new boolean[]{true,true,true,false,false,false})
                .setContentTextSize(18)
                .build();
        pvCustomTime.show();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
