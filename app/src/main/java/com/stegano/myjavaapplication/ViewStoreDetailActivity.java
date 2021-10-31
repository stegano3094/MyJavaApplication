package com.stegano.myjavaapplication;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.stegano.myjavaapplication.datas.Store;

import java.util.List;

public class ViewStoreDetailActivity extends BaseActivity {
    Store mStoreData;
    ImageView storeLogoImg;
    TextView storePhoneNumberTxt;
    TextView storeNameTxt;
    Button callPhoneBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_store_detail);

        storeLogoImg = findViewById(R.id.storeLogoImg);
        storePhoneNumberTxt = findViewById(R.id.storePhoneNumberTxt);
        storeNameTxt = findViewById(R.id.storeNameTxt);
        callPhoneBtn = findViewById(R.id.callPhoneBtn);

        setupEvents();
        setValues();

    }

    @Override
    void setupEvents() {

        callPhoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT).show();

                        Uri myUri = Uri.parse("tel:" + mStoreData.getPhoneNumber());
                        Intent myIntent = new Intent(Intent.ACTION_CALL, myUri);
                        startActivity(myIntent);
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(mContext, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
                    }
                };

                TedPermission.create()
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("Please turn on permissions at [Setting] > [Permission]")
                        .setPermissions(Manifest.permission.CALL_PHONE)
                        .check();
            }
        });
    }

    @Override
    void setValues() {
        mStoreData = (Store) getIntent().getSerializableExtra("storeData");

        Glide.with(mContext)
                .load(mStoreData.getLogoUrl())
                .error(R.drawable.logo_main)
                .into(storeLogoImg);
        storeNameTxt.setText(mStoreData.getName());
        storePhoneNumberTxt.setText(mStoreData.getPhoneNumber());
    }
}