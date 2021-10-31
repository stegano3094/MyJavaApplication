package com.stegano.myjavaapplication;

import android.content.Context;

import androidx.appcompat.app.AppCompatActivity;

abstract class BaseActivity extends AppCompatActivity {
    Context mContext = this;

    abstract void setupEvents();  // 이벤트 처리
    abstract void setValues();  // 화면 표시
}