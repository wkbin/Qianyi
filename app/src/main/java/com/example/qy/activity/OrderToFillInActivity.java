package com.example.qy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qy.R;
import com.example.qy.whs.BaseActivity;

public class OrderToFillInActivity extends BaseActivity {
    private ImageView iv_phone_book;
    private Intent mIntent;

    private static final int PICK_CONTACT = 1;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 2;

    private EditText et_name;
    private EditText et_phone;
    private TextView tv_clear;
    private TextView tv_commit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_to_fill_in);

        init("订单填写");

        et_name = findViewById(R.id.et_name);
        et_phone = findViewById(R.id.et_phone);

        iv_phone_book = findViewById(R.id.iv_phone_book);
        tv_clear = findViewById(R.id.tv_clear);
        tv_clear.setOnClickListener(v -> {
            et_phone.setText("");
            et_name.setText("");
        });
        iv_phone_book.setOnClickListener(v -> {
            selectConnection();
        });

        tv_commit = findViewById(R.id.tv_commit);
        tv_commit.setOnClickListener(v -> {
            startActivity(new Intent(this,PayActivity.class));
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_CONTACT:
                mIntent = data;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
                    //申请授权，第一个参数为要申请用户授权的权限；第二个参数为requestCode 必须大于等于0，主要用于回调的时候检测，匹配特定的onRequestPermissionsResult。
                    //可以从方法名requestPermissions以及第二个参数看出，是支持一次性申请多个权限的，系统会通过对话框逐一询问用户是否授权。
                    requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);

                }else{
                    //如果该版本低于6.0，或者该权限已被授予，它则可以继续读取联系人。
                    getContacts(data);
                }
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 用户成功授予权限
                getContacts(mIntent);
            } else {
                Toast.makeText(this, "你拒绝了此应用对读取联系人权限的申请！", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void getContacts(Intent data) {
        if (data == null) {
            return;
        }

        Uri contactData = data.getData();
        if (contactData == null) {
            return;
        }
        String name = "";
        String phoneNumber = "";

        Uri contactUri = data.getData();
        Cursor cursor = getContentResolver().query(contactUri, null, null, null, null);
        if (cursor.moveToFirst()) {
            name = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            String hasPhone = cursor
                    .getString(cursor
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
            String id = cursor.getString(cursor
                    .getColumnIndex(ContactsContract.Contacts._ID));
            if (hasPhone.equalsIgnoreCase("1")) {
                hasPhone = "true";
            } else {
                hasPhone = "false";
            }
            if (Boolean.parseBoolean(hasPhone)) {
                Cursor phones = getContentResolver()
                        .query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                        + " = " + id, null, null);
                while (phones.moveToNext()) {
                    phoneNumber = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                }
                phones.close();
            }
            cursor.close();

            et_name.setText(name);
            et_phone.setText(phoneNumber);
        }
    }

    private void selectConnection() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
    }
}
