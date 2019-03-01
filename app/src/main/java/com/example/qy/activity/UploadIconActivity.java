package com.example.qy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.qy.R;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class UploadIconActivity extends BaseActivity {
    private de.hdodenhof.circleimageview.CircleImageView iv_icon;

    private Button btn_select_photo;
    private Button btn_upload_photo;
    private Button btn_taking_pictures;
    private Uri imageUri;
    private String key;
    private String phone;
    private static final String TAG = "UploadIconActivity";
    // 要申请的权限
    private List<String> permissionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_icon);

        phone = ((MyApplication) getApplication()).getUserInfo().phone;
        key = "icon_" + phone + ".jpg";
        initView();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            imageUri = UCrop.getOutput(data);
            Log.d("UploadIconAcitvity", "uri == " + imageUri.toString());
            iv_icon.setImageURI(imageUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        } else if (requestCode == 202) {
            if (data != null) {
                ImageUtils.startPhotoCrop(UploadIconActivity.this,data.getData());
            }
        } else if (requestCode == 222) {

            ImageUtils.startPhotoCrop(UploadIconActivity.this,imageUri);

            Log.d(TAG, "777");
        }
    }


    /**
     * 打开系统相册
     */
    private void openSysAlbum() {
        iv_icon.setImageDrawable(null);
        Intent albumIntent = new Intent(Intent.ACTION_PICK);
        albumIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(albumIntent, 202);
    }

    /**
     * 打开系统相机
     */
    private void openSysCamera() {
        File outputImage = new File(Environment.getExternalStorageDirectory(),
                key);
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
            imageUri = FileProvider.getUriForFile(UploadIconActivity.this, this.getApplicationContext().getPackageName() + ".provider", outputImage);
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
        // 设置图片名字
        Log.d(TAG, "key = " + key);
        uploadManager.put(b, key, token, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject res) {
                        // info.error中包含了错误信息，可打印调试
                        // 上传成功后将key值上传到自己的服务器

                        if (info.isOK()) {
                            ToastUtils.showShort(UploadIconActivity.this, "上传成功");
                            Log.i("qiniu", "Upload Success");

                            String url = (HttpQYUtils.getUpdateIcon(phone, "http://pnb0vwgfl.bkt.clouddn.com/" + key));

                            Log.d(TAG, "头像上传 url = " + url);
                            HttpUtils.sendOkHttpRequest(url, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showShort(UploadIconActivity.this, "插入服务器失败");
                                        }
                                    });

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    String responseText = response.body().string();
                                    try {
                                        JSONObject jsonObject = new JSONObject(responseText);
                                        final String msg = jsonObject.getString("msg");
                                        boolean isSuc = jsonObject.getBoolean("isSuc");
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                ToastUtils.showShort(UploadIconActivity.this, msg);
                                            }
                                        });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });

                        } else {
                            ToastUtils.showShort(UploadIconActivity.this, "上传失败");
                            Log.i("qiniu", "Upload Fail");
                            //如果失败，这里可以把info信息上报自己的服务器，便于后面分析上传错误原因
                        }
                    }
                },
                new UploadOptions(null, null, false, new UpProgressHandler() {
                    @Override
                    public void progress(String key, double percent) {
                        Log.i("qiniu", key + ": " + percent);
                    }
                }, null));
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
                ToastUtils.showShort(UploadIconActivity.this,"未知错误");
            }
        }
    }

    private void initView() {
        permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(UploadIconActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(UploadIconActivity.this,Manifest.permission.CAMERA )!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(UploadIconActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(UploadIconActivity.this,Manifest.permission.ACCESS_FINE_LOCATION )!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }


        iv_icon = findViewById(R.id.iv_icon);
        btn_select_photo = findViewById(R.id.btn_select_photo);
        btn_upload_photo = findViewById(R.id.btn_upload_photo);
        btn_taking_pictures = findViewById(R.id.btn_taking_pictures);
        btn_select_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSysAlbum();
            }
        });
        btn_upload_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 如果没有相机权限，申请打开相机

                if (imageUri != null) {

                    String tokenUrl = HttpQYUtils.getIconToken(key);
                    HttpUtils.sendOkHttpRequest(tokenUrl, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ToastUtils.showShort(UploadIconActivity.this, "获取token失败");
                                }
                            });
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String responseText = response.body().string();
                            try {
                                JSONObject jsonObject = new JSONObject(responseText);
                                boolean isSuc = jsonObject.getBoolean("isSuc");
                                final String msg = jsonObject.getString("msg");
                                JSONObject jsonData = jsonObject.getJSONObject("data");
                                if (isSuc) {
                                    String token = jsonData.getString("token");
                                    byte[] b = ImageUtils.getImgByteFromUri(UploadIconActivity.this,imageUri);
                                    uploadImageToQiniu(b, token);
                                } else {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ToastUtils.showShort(UploadIconActivity.this, msg);
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        btn_taking_pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 版本判断。当手机系统大于 23 时，才有必要去判断权限是否获取

                if (!permissionList.isEmpty()){
                    String []permission = permissionList.toArray(new String[permissionList.size()]);
                    ActivityCompat.requestPermissions(UploadIconActivity.this,permission,1);
                }else{
                    openSysCamera();
                }



            }
        });
    }

}
