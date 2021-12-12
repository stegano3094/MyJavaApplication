package com.stegano.myjavaapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.stegano.myjavaapplication.service.MyService;

public class ForegroundServiceActivity extends AppCompatActivity {
    private static final String TAG = "ForegroundServiceActivity";

    Button onStartService;
    Button onStopService;
    Button onStartForegroundService;
    Button getCountValue;

    private MyService myService;  // 서비스
    private boolean mBound;  // 바인드 되었는지 확인하는 플래그

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foreground_service);

        onStartService = findViewById(R.id.onStartService);
        onStopService = findViewById(R.id.onStopService);
        onStartForegroundService = findViewById(R.id.onStartForegroundService);
        getCountValue = findViewById(R.id.getCountValue);

        onStartService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                startService(intent);  // 서비스 시작

                // (여러번 누를 때) 이거 추가해주어야 "서비스 시작" 버튼 누르면 정상적으로 서비스 시작됨.
                Intent intent2 = new Intent(getApplicationContext(), MyService.class);
                bindService(intent2, mConnection, BIND_AUTO_CREATE);  // BIND_AUTO_CREATE - 서비스를 자동으로 생성해주고 바인드까지 해준다.
            }
        });

        onStopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                stopService(intent);  // 서비스 중지

                // (여러번 누를 때) 이거 추가해주어야 "서비스 중지" 버튼 누르면 정상적으로 서비스 중지됨.
                if (mBound) {
                    unbindService(mConnection);
                    mBound = false;
                }
            }
        });

        onStartForegroundService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                intent.setAction("startForeground");

                // startForegroundService()는 오레오 이상에서만 동작한다.
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);  // 포그라운드 서비스 시작 (프그라운드로 승격)
                } else {
                    startService(intent);  // 서비스 시작
                }
            }
        });

        getCountValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBound) {
                    Toast.makeText(getApplicationContext(), "카운팅 : " + myService.getCount(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    // 일반적으로 바인드는 onStart()에서 한다.
    @Override
    protected void onStart() {
        super.onStart();

        Intent intent = new Intent(this, MyService.class);
        bindService(intent, mConnection, BIND_AUTO_CREATE);  // BIND_AUTO_CREATE - 서비스를 자동으로 생성해주고 바인드까지 해준다.
    }

    // 바인드를 끊지 않으면 레퍼런스를 잃어버리고 메모리 누수가 일어난다.
    @Override
    protected void onStop() {
        super.onStop();

        if (mBound) {
            unbindService(mConnection);
            mBound = false;  // 종료 시 플래그를 false로 둔다.
        }
    }

    // 서비스 커넥션을 만들어 주어야 한다.
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            // 바인드가 연결되었을 때
            MyService.MyBinder binder = (MyService.MyBinder) service;
            myService = binder.getService();
            mBound = true;  // 바인드 되었으므로 true로 둔다.
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // 예기치 않은 종료 시 (ex, 안드로이드 시스템이 죽일 때)
        }
    };
}