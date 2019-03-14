package com.example.qy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.contrarywind.view.WheelView;
import com.example.qy.R;
import com.example.qy.bean.CityBean;
import com.example.qy.bean.User;
import com.example.qy.bean.UserInfo;
import com.example.qy.ui.IconChooseDialog;
import com.example.qy.ui.SexChooseDialog;
import com.example.qy.utils.HttpQYUtils;
import com.example.qy.utils.HttpUtils;
import com.example.qy.utils.ImageUtils;
import com.example.qy.utils.ToastUtils;
import com.example.qy.whs.BaseActivity;
import com.example.qy.whs.MyApplication;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.yalantis.ucrop.UCrop;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    private LinearLayout li_nickname;
    private LinearLayout li_sex;
    private LinearLayout li_birthday;
    private LinearLayout li_address;
    private LinearLayout li_signature;
    private TimePickerView pvCustomTime;
    private OptionsPickerView pvCustomOptions;
    private List<CityBean> lists;
    private List<List<String>> cityLists;
    private TextView tv_birthday;
    private TextView tv_address;
    private TextView tv_sex;
    private TextView tv_name;
    private TextView tv_signature;

    private ImageView action_bar_iv_left;
    private TextView action_bar_text;

    private TextView action_bar_iv_right;

    private CircleImageView civ_icon;

    private MyApplication application;
    private String name;
    private String sex;
    private String birthday;
    private String address;
    private String signature;

    // 上传头像所需
    private int id;
    private String key;
    private Uri imageUri;
    // 要申请的权限
    private List<String> permissionList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_personal_data);

        rl_choose_icon = findViewById(R.id.rl_choose_icon);

        li_sex = findViewById(R.id.li_sex);
        li_birthday = findViewById(R.id.li_birthday);
        li_address = findViewById(R.id.li_address);
        li_signature = findViewById(R.id.li_signature);
        li_nickname = findViewById(R.id.li_nickname);

        tv_birthday = findViewById(R.id.tv_birthday);
        tv_address = findViewById(R.id.tv_address);
        civ_icon = findViewById(R.id.civ_icon);
        tv_sex = findViewById(R.id.tv_sex);
        action_bar_text = findViewById(R.id.action_bar_text);
        action_bar_iv_left = findViewById(R.id.action_bar_iv_left);
        tv_name = findViewById(R.id.tv_name);
        action_bar_iv_right = findViewById(R.id.action_bar_iv_right);
        tv_signature = findViewById(R.id.tv_signature);

        rl_choose_icon.setOnClickListener(this);
        li_sex.setOnClickListener(this);
        li_birthday.setOnClickListener(this);
        li_address.setOnClickListener(this);
        li_signature.setOnClickListener(this);
        li_nickname.setOnClickListener(this);

        action_bar_iv_left.setOnClickListener(this);
        action_bar_iv_right.setOnClickListener(this);

        initData();
    }

    private void initData(){
        action_bar_text.setText("修改资料");
        application = (MyApplication) getApplication();
        UserInfo userInfo = application.getUserInfo();

        id = userInfo.loginId;
//        key = "icon_" + phone + ".jpg";
        permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(DetailedPersonalDataActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(DetailedPersonalDataActivity.this,Manifest.permission.CAMERA )!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(DetailedPersonalDataActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(DetailedPersonalDataActivity.this,Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        Glide.with(DetailedPersonalDataActivity.this).load(userInfo.icon).into(civ_icon);
        tv_birthday.setText(userInfo.birthday);
        tv_address.setText(userInfo.home);
        tv_sex.setText(userInfo.sex);
        tv_name.setText(userInfo.nickname);
        tv_signature.setText(userInfo.signature);

        Log.d("888","userInfo = "+userInfo.toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_choose_icon:
                IconChooseDialog dialog = new IconChooseDialog(DetailedPersonalDataActivity.this);
                dialog.setOnClickListener(new IconChooseDialog.onClickListener() {

                    public void onPicturesClick() {
                        // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取

                        if (!permissionList.isEmpty()){
                            String []permission = permissionList.toArray(new String[permissionList.size()]);
                            ActivityCompat.requestPermissions(DetailedPersonalDataActivity.this,permission,1);
                        }else{
                            openSysCamera();
                        }
                        dialog.dismiss();
                    }

                    public void onGalleryClick() {
                        openSysAlbum();
                        dialog.dismiss();
                    }
                    @Override
                    public void onCancleClick() {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.li_sex:
                SexChooseDialog d = new SexChooseDialog(DetailedPersonalDataActivity.this);
               d.isBoy = (tv_sex.getText().equals("男"))?true:false;
                d.setOnClickListener(new SexChooseDialog.OnClickListener() {
                    @Override
                    public void onConfirmClick(boolean isBoy) {
                        if (isBoy){
                            tv_sex.setText("男");
                        }else{
                            tv_sex.setText("女");
                        }
                        d.dismiss();
                    }

                    @Override
                    public void onCancelClick() {
                        d.dismiss();
                    }
                });
                d.show();
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
            case R.id.li_signature:
                Intent i2 = new Intent(DetailedPersonalDataActivity.this,IndividualitySignatureActivity.class);
                i2.putExtra("signature",tv_signature.getText().toString().trim());
                startActivityForResult(i2,1);
                break;
            case R.id.li_nickname:
                Intent intent = new Intent(DetailedPersonalDataActivity.this,NicknameActivity.class);
                intent.putExtra("name",tv_name.getText().toString().trim());
                startActivityForResult(intent,2);
                break;
            case R.id.action_bar_iv_right:
                // 保存
                 name = tv_name.getText().toString().trim();
                 sex = tv_sex.getText().toString().trim();
                 birthday = tv_birthday.getText().toString().trim();
                 address = tv_address.getText().toString().trim();
                 signature = tv_signature.getText().toString().trim();
                // 存储头像


                // 以时间戳命名
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                key   = "icon_" + id + "_" + df.format(new Date())+".jpg";
                if (imageUri != null) {
                    String tokenUrl = HttpQYUtils.getIconToken(key);
                    HttpUtils.sendOkHttpRequest(tokenUrl, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(()-> {
                                ToastUtils.showShort(DetailedPersonalDataActivity.this, "获取token失败");
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(responseText);
                                boolean isSuc = jsonObject.getBoolean("isSuc");
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                if (isSuc) {
                                    String token = jsonData.getString("token");
                                    // 图片压缩1M以内
                                    byte[] b = ImageUtils.getImgByteFromUri(DetailedPersonalDataActivity.this,imageUri);
                                    uploadImageToQiniu(b, token);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                HttpUtils.sendOkHttpRequest(HttpQYUtils.getMaterial(id, name, sex, birthday, address, signature), new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        runOnUiThread(()-> {
                            ToastUtils.showShort(DetailedPersonalDataActivity.this, "连接断开");
                        });
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String responseText = response.body().string();
                        try {
                            JSONObject jsonObject = new JSONObject(responseText);
                            String msg = jsonObject.getString("msg");
                            if (jsonObject.getBoolean("isSuc")){
                                // TODO 存储个人信息
                                UserInfo userInfo = application.getUserInfo();
                                userInfo.nickname = name;
                                userInfo.sex = sex;
                                userInfo.birthday = birthday;
                                userInfo.home = address;
                                userInfo.signature = signature;
                                application.setUserInfo(userInfo);
                                setResult(RESULT_OK);
                                finish();
                            }else{
                                runOnUiThread(()-> {
                                    ToastUtils.showShort(DetailedPersonalDataActivity.this, msg);
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

//                Log.d("888","name = "+name+"\nsex = "+sex+"\nbirthday = "+birthday+"\naddress = "+address+"\nsignature = "+signature);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1){
                tv_signature.setText(data.getStringExtra("signature"));
            }
            if (requestCode == 2){
                tv_name.setText(data.getStringExtra("nickname"));
            }

            if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
                civ_icon.setImageDrawable(null);
                imageUri = UCrop.getOutput(data);
                civ_icon.setImageURI(imageUri);
            } else if (resultCode == UCrop.RESULT_ERROR) {
                final Throwable cropError = UCrop.getError(data);
            } else if (requestCode == 202) {
                if (data != null) {
                    ImageUtils.startPhotoCrop(DetailedPersonalDataActivity.this,data.getData());
                }
            } else if (requestCode == 222) {
                ImageUtils.startPhotoCrop(DetailedPersonalDataActivity.this,imageUri);

            }
        }

    }
    // 权限申请回调
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0){
                for (int result : grantResults){
                    if (result != PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this,"需要同意权限才能使用相机",Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                openSysCamera();
            }else{
                ToastUtils.showShort(DetailedPersonalDataActivity.this,"未知错误");
            }
        }
    }
    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, 202);
    }

    /**
     * 打开系统相机
     */
    private void openSysCamera() {
        // 以时间戳命名
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        key   = "icon_" + id + "_" + df.format(new Date())+".jpg";
        File outputImage = new File(Environment.getExternalStorageDirectory(), key);
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT < 24){
            imageUri = Uri.fromFile(outputImage);
        }else{
            // 7.0 之后只能如此使用
            imageUri = FileProvider.getUriForFile(DetailedPersonalDataActivity.this, this.getApplicationContext().getPackageName() + ".provider", outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, 222);

    }

    /**
     * 上传图片到七牛
     *
     * @param token    在七牛官网上注册的token
     */
    private void uploadImageToQiniu(byte[] b, String token) {
        UploadManager uploadManager = new UploadManager();

        uploadManager.put(b, key, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        // info.error中包含了错误信息，可打印调试
                        // 上传成功后将key值上传到自己的服务器

                        if (info.isOK()) {
                            ToastUtils.showShort(DetailedPersonalDataActivity.this, "上传成功");
                            Log.i("qiniu", "Upload Success");

                            String url = (HttpQYUtils.getUpdateIcon(id, "http://pnb0vwgfl.bkt.clouddn.com/" + key));
                            HttpUtils.sendOkHttpRequest(url, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(()-> {
                                            ToastUtils.showShort(DetailedPersonalDataActivity.this, "插入服务器失败");
                                    });
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseText = response.body().string();
                                    try {
                                        JSONObject jsonObject = new JSONObject(responseText);
                                        final String msg = jsonObject.getString("msg");
                                        boolean isSuc = jsonObject.getBoolean("isSuc");
                                        runOnUiThread(()-> {
                                                ToastUtils.showShort(DetailedPersonalDataActivity.this, msg);
                                        });
                                        if (isSuc){
                                            //TODO 存储头像
                                            UserInfo userInfo = application.getUserInfo();
                                            userInfo.icon = "http://pnb0vwgfl.bkt.clouddn.com/"+key;
                                            application.setUserInfo(userInfo);
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else {
                            ToastUtils.showShort(DetailedPersonalDataActivity.this, "上传失败");
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                    }
                },
                new UploadOptions(null, null, false, (key,percent)-> {
                        Log.i("qiniu", key + ": " + percent);
                }, null));
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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
