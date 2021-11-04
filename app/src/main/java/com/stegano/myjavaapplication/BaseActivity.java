package com.stegano.myjavaapplication;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


// 응용 프로그램을 구성하는 4가지 컴포넌트 : 액티비티, 서비스, Broadcast Receiver(BR), Content Provider(CP)
abstract class BaseActivity extends AppCompatActivity {
    Context mContext = this;
    Typeface typeface;
    TextView textView;

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