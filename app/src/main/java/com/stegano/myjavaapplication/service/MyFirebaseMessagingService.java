package com.stegano.myjavaapplication.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.stegano.myjavaapplication.MainActivity;
import com.stegano.myjavaapplication.R;

/**
 * 참고 자료
 * https://firebase.google.com/docs/cloud-messaging/android/client?hl=ko#java_1
 * https://blog.naver.com/PostView.naver?blogId=ndb796&logNo=221553341369&redirect=Dlog&widgetTypeCall=true&directAccess=false
 *
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String TAG = "MyFirebaseMessagingService";

    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        Log.d(TAG, "onMessageReceived()");
//        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "onMessageReceived() if");
            String messageTitle = remoteMessage.getNotification().getTitle();
            String messageBody = remoteMessage.getNotification().getBody();

            Log.d(TAG, "onMessageReceived() messageTitle : " + messageTitle);
            Log.d(TAG, "onMessageReceived() messageBody : " + messageBody);


            // Notification (커스텀 알림, 이거 없이 super.onMessageReceived(remoteMessage); 주석 해제하면 잘 나옴)
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    this,
                    102,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT);

            String channelId = "channelId";
            Uri defaultSountUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.logo_main)
                    .setContentTitle(messageTitle)
                    .setContentText(messageBody)
                    .setSound(defaultSountUri)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                String channelName = "channelName";
                NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0, builder.build());
        }
    }
}
