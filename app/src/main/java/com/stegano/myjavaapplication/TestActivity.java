package com.stegano.myjavaapplication;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.stegano.myjavaapplication.adapters.MainViewPagerAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        setupEvents();
        setValues();

    }

    @Override
    void setupEvents() {

    }

    @Override
    void setValues() {
    }
}