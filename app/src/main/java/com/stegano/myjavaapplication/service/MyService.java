package com.stegano.myjavaapplication.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.stegano.myjavaapplication.R;
import com.stegano.myjavaapplication.SplashActivity;

public class MyService extends Service {
    private static final String TAG = "MyService";

    private Thread mThread;
    private int mCount = 0;

    public MyService() {
    }

    // Service 생명주기
    // onStartService()요청 -> onCreate() -> onStartCommand() ->
    // 서비스 중 -> 서비스 중지 요청 -> onStopService() -> onDestroy() -> 종료
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if("startForeground".equals(intent.getAction())) {
            startForeGroundService();  // startForeground라는 액션이 들어올 때에는 포그라운드로 시작한다.
        } else {
            // 여기에 백그라운드나 오래 걸리는 작업을 하면 된다. (백그라운드
            if (mThread == null) {
                mThread = new Thread("My Thread") {
                    @Override
                    public void run() {
                        for (int i = 0; i < 60; i++) {
                            try {
                                mCount++;
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                break;
                            }
                            Log.d(TAG, "MyService 동작중 " + mCount);
                        }
                    }
                };
                mThread.start();
            }
        }

        return START_STICKY;  // 서비스가 종료되어도 다시 서비스가 시작된다.
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d(TAG, "MyService onDestroy()");

        if(mThread != null) {
            mThread.interrupt();  // 예외를 발생시켜 스레드를 종료시킨다.
            mThread = null;
            mCount = 0;
        }
    }

    private void startForeGroundService() {
        // 알림을 사용할 때 오레오 이상은 채널이 필요함
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "id");
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("포그라운드 서비스");
        builder.setContentText("포그라운드 서비스 실행 중");

        // 알림 클릭 시 실제로 동작
        Intent notificationIntent = new Intent(this, SplashActivity.class);

        // 잠시 대기해 놓고 실행하는 PendingIntent를 설정한다. (마지막의 플래그는 일반적으로 0을 넣어준다.)
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        builder.setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            manager.createNotificationChannel(
                    new NotificationChannel("id", "기본채널", NotificationManager.IMPORTANCE_DEFAULT)
            );
        }

        startForeground(1, builder.build());
    }

    // Bind 관련
    private IBinder mBinder = new MyBinder();  // Binder는 IBinder를 implements하고 있다.

    public class MyBinder extends Binder {
        public MyService getService() {
            return MyService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {  // onBind()는 연결고리를 만드는 것과 같다.
        return mBinder;
    }

    public int getCount() {
        return mCount;
    }
}