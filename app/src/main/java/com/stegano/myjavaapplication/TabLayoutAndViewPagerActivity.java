package com.stegano.myjavaapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.stegano.myjavaapplication.adapters.MainViewPagerAdapter;

public class TabLayoutAndViewPagerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout_and_view_pager);

        setupEvents();
        setValues();

    }

    @Override
    void setupEvents() {

    }

    @Override
    void setValues() {
        // 뷰페이저
        ViewPager mainViewPager = findViewById(R.id.mainViewPager);
        MainViewPagerAdapter mvpa = new MainViewPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(mvpa);

        // 탭레이아웃
        TabLayout mainTabLayout = (TabLayout) findViewById(R.id.mainTabLayout);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }
}