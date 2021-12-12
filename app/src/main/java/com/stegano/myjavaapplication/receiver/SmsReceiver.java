package com.stegano.myjavaapplication.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import com.stegano.myjavaapplication.ReceiverActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {
    private static final String TAG = "SmsReceiver";
    public SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onReceive(Context context, Intent intent) {  // 인텐트(SMS)를 전달받았을 때 자동으로 호출되는 메서드
        Log.i(TAG, "onReceive()");

        Bundle bundle = intent.getExtras();  // intent에서 Bundle 객체를 가져옴
        SmsMessage[] messages = parseSmsMessage(bundle);

        if (messages != null && messages.length > 0) {
            String sender = messages[0].getOriginatingAddress();
            Log.i(TAG, "SMS sender : " + sender);

            String contents = messages[0].getMessageBody();
            Log.i(TAG, "SMS contents : " + contents);

            Date receiveDate = new Date(messages[0].getTimestampMillis());
            Log.i(TAG, "SMS receiveDate : " + receiveDate.toString());

            sendToActivity(context, sender, contents, receiveDate);  // 화면 띄우기
        }
    }

    private SmsMessage[] parseSmsMessage(Bundle bundle) {
        Object[] objs = (Object[]) bundle.get("pdus");  // Bundle 객체에 있는 데이터 중 pdus를 가져옴
        SmsMessage[] messages = new SmsMessage[objs.length];

        int smsCount = objs.length;

        for (int i = 0; i < smsCount; i++) {
            String format = bundle.getString("format");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i], format);
            } else {
                messages[i] = SmsMessage.createFromPdu((byte[]) objs[i]);
            }
        }

        return messages;
    }

    private void sendToActivity(Context context, String sender, String contents, Date receiveDate) {
        Intent intent = new Intent(context, ReceiverActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  // 브로드캐시트 수신자는 액티비티가 없으므로 새로운 태스크를 만들어주어야 함
                Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);  // 액티비티 중복생성 방지
        intent.putExtra("sender", sender);
        intent.putExtra("contents", contents);
        intent.putExtra("receiveDate", receiveDate);
        context.startActivity(intent);  // 화면 띄우기
    }
}