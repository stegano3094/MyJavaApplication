package com.stegano.myjavaapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.stegano.myjavaapplication.dto.UserDTO;

public class MainActivity extends BaseActivity {
    public Button loginBtn;
    public EditText emailEdt;
    public EditText passwordEdt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupEvents();
        setValues();
    }

    @Override
    void setupEvents() {
        loginBtn = (Button) findViewById(R.id.loginBtn);
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 입력한 아이디, 비밀번호를 변수에 저장
                String inputEmail = emailEdt.getText().toString();
                String inputPassword = passwordEdt.getText().toString();

                // 서버에 회원이 맞는지 확인 요청 (Request)

                // 테스트용으로 화면 이동
                Intent intent = new Intent(MainActivity.this, TestActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    void setValues() {

        // Gson 사용방법 (dto/UserDTO 파일 참조)
        String json = "{\"name\":\"식빵\", \"age\":31, \"city\":\"New York\"}";
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);

        if(BuildConfig.DEBUG) {
            Log.d("MainActivity", userDTO.getCity());
            Log.d("MainActivity", userDTO.getName());
            Log.d("MainActivity", ""+userDTO.getAge());
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            showToast("가로방향");
        } else if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            showToast("세로방향");
        }
    }

    public void showToast(String data) {
        Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
    }
}