package com.stegano.myjavaapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class TestActivity extends AppCompatActivity {
    static final String TAG = "TestActivity";

    private Spinner timeSpinner;
    private Spinner timeSpinner2;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        timeSpinner2 = (Spinner) findViewById(R.id.timeSpinner2);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        resultTextView.setTextSize(24f);


    }

}