package com.stegano.myjavaapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class TestActivity extends BaseActivity {
    static final String TAG = "TestActivity";

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