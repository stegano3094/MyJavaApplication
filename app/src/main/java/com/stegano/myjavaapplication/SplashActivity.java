package com.stegano.myjavaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends BaseActivity {
    private static String TAG = "SplashActivity";

    TextView splashTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        splashTextView = findViewById(R.id.splashTextView);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(mContext, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    void setupEvents() {

    }

    @Override
    void setValues() {
        // 파이어베이스에서 현재 토큰 가져오기
        FirebaseMessaging.getInstance()
                .getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }

                        // Get new FCM registration token
                        String token = task.getResult();
                        Log.d(TAG, "token : " + token);
                        Toast.makeText(mContext, "토큰 : " + token, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}