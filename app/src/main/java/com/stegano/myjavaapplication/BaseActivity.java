package com.stegano.myjavaapplication;

import android.content.Context;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;

abstract class BaseActivity extends AppCompatActivity {
    Context mContext = this;
    Typeface typeface;

    abstract void setupEvents();  // 이벤트 처리
    abstract void setValues();  // 화면 표시

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);

        if (typeface == null) {
            //typeface = Typeface.createFromAsset(this.getAssets(), "font/hs_new.ttf");
        }
    }
}