package com.example.qy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.contrarywind.view.WheelView;
import com.example.qy.R;
import com.example.qy.bean.CityBean;
import com.example.qy.bean.UserInfo;
import com.example.qy.ui.IconChooseDialog;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DetailedPersonalDataActivity extends BaseActivity implements View.OnClickListener {
    private RelativeLayout rl_choose_icon;
    private LinearLayout li_sex;
    private LinearLayout li_birthday;
    private LinearLayout li_address;
    private TimePickerView pvCustomTime;
    private OptionsPickerView pvCustomOptions;
    private List<CityBean> lists;
    private List<List<String>> cityLists;
    private TextView tv_birthday;
    private TextView tv_address;
    private TextView tv_sex;
    private TextView tv_name;

    private ImageView action_bar_iv_left;
    private TextView action_bar_text;

    private CircleImageView civ_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_personal_data);

        rl_choose_icon = findViewById(R.id.rl_choose_icon);

        li_sex = findViewById(R.id.li_sex);
        li_birthday = findViewById(R.id.li_birthday);
        li_address = findViewById(R.id.li_address);
        tv_birthday = findViewById(R.id.tv_birthday);
        tv_address = findViewById(R.id.tv_address);
        civ_icon = findViewById(R.id.civ_icon);
        tv_sex = findViewById(R.id.tv_sex);
        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        tv_name = findViewById(R.id.tv_name);

        rl_choose_icon.setOnClickListener(this);
        li_sex.setOnClickListener(this);
        li_birthday.setOnClickListener(this);
        li_address.setOnClickListener(this);

        action_bar_iv_left.setOnClickListener(this);

        initData();
    }

    private void initData(){
        action_bar_text.setText("修改资料");
        MyApplication application = (MyApplication) getApplication();
        UserInfo userInfo = application.getUserInfo();
        Glide.with(DetailedPersonalDataActivity.this).load(userInfo.icon).into(civ_icon);
        tv_birthday.setText(userInfo.birthday);
        tv_address.setText(userInfo.home);
        tv_sex.setText(userInfo.sex);
        tv_name.setText(userInfo.nickname);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_choose_icon:
                IconChooseDialog dialog = new IconChooseDialog(DetailedPersonalDataActivity.this);
                dialog.setOnClickListener(new IconChooseDialog.onClickListener() {
                    @Override
                    public void onPicturesClick() {
                        ToastUtils.showShort(DetailedPersonalDataActivity.this,"拍一张");
                    }
                    @Override
                    public void onGalleryClick() {
                        ToastUtils.showShort(DetailedPersonalDataActivity.this,"相册选择");
                    }
                    @Override
                    public void onCancleClick() {
                        ToastUtils.showShort(DetailedPersonalDataActivity.this,"取消");
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.li_sex:
                break;
            case R.id.li_birthday:
                showBirthdayDialog();
                break;
            case R.id.li_address:
                String url = HttpQYUtils.getAddress();
                HttpUtils.sendOkHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastUtils.showShort(DetailedPersonalDataActivity.this,"连接断开");
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
            case R.id.action_bar_iv_left:
                finish();
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
        pvCustomTime = new TimePickerBuilder(this, (date, v) -> {
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy - MM - dd");
        return format.format(date);
    }
}
