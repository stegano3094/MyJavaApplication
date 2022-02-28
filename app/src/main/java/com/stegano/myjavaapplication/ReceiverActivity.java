package com.stegano.myjavaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class ReceiverActivity extends AppCompatActivity {
    static final String TAG = "ReceiverActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver);

        // 브로드캐스트 리시버는 앱을 실행하지 않아도 인텐트를 전달받을 수 있다.
//        AndPermission.with(this)  // 모든 위험 권한을 자동 부여하도록 하는 메서드
//                .runtime()
//                .permission(Permission.RECEIVE_SMS)
//                .onGranted(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//                        showToast("허용된 권한 개수 : " + data.size());
//                    }
//                })
//                .onDenied(new Action<List<String>>() {
//                    @Override
//                    public void onAction(List<String> data) {
//                        showToast("거부된 권한 개수 : " + data.size());
//                    }
//                })
//                .start();


    }

    private void showToast(String text) {
        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
    }
}