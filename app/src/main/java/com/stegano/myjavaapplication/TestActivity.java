package com.stegano.myjavaapplication;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class TestActivity extends AppCompatActivity {
    static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        makeDir();
    }

    public void makeDir() {
        //디렉토리 생성
        String path = getPath();
        Log.d(TAG, "path : " + path);

        File file = new File(path + "my_img");
        file.mkdir();

        String msg = "디렉토리 생성";
        if(!file.isDirectory())
            msg = "디렉토리 생성중 오류 발생";
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public String getPath() {
        String sdPath = "";
        String ext = Environment.getExternalStorageState();

        if (ext.equals(Environment.MEDIA_MOUNTED)) {
            sdPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
            //sdPath = "mnt/sdcard/";
        } else {
            sdPath = getFilesDir() + "";
        }
        Toast.makeText(getApplicationContext(), sdPath, Toast.LENGTH_SHORT).show();


        // 내부 저장소
        File fileCacheDir = getCacheDir();
        String getCacheDir = fileCacheDir.getPath();
        Log.d(TAG, "getCacheDir : " + getCacheDir);  // -> getCacheDir : /data/user/0/com.stegano.myjavaapplication/cache

        File fileDataBase = getDatabasePath("Android");
        String getDatabasePath = fileDataBase.getPath();
        Log.d(TAG, "getDatabasePath : " + getDatabasePath);  // -> getDatabasePath : /data/user/0/com.stegano.myjavaapplication/databases/Android

        File fileFile = getFilesDir();
        String getFile = fileFile.getPath();
        Log.d(TAG, "getFile : " + getFile);  // -> getFile : /data/user/0/com.stegano.myjavaapplication/files

        File fileFileName = getFileStreamPath("Android");
        String getFileName = fileFileName.getPath();
        Log.d(TAG, "getFileName : " + getFileName);  // -> getFileName : /data/user/0/com.stegano.myjavaapplication/files/Android


        // 외부 저장소 (어플리케이션 고유 영역)
        String getCache2 = getExternalCacheDir() + "/tmp";
        Log.d(TAG, "getCache2 : " + getCache2);  // -> getCache2 : /storage/emulated/0/Android/data/com.stegano.myjavaapplication/cache/tmp

        File[] fileAlarms2 = getExternalFilesDirs(Environment.DIRECTORY_ALARMS);
        String getAlarms2 = fileAlarms2[0].getPath();
        Log.d(TAG, "getAlarms2 : " + getAlarms2);  // -> getAlarms2 : /storage/emulated/0/Android/data/com.stegano.myjavaapplication/files/Alarms


        // 외부 저장소 (공용 영역)
        String getDirectory = Environment.getExternalStorageDirectory() + "/tmp";
        Log.d(TAG, "getDirectory : " + getDirectory);  // -> getDirectory : /storage/emulated/0/tmp

        File fileAlarms = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS);
        String getAlarms = fileAlarms.getPath();
        Log.d(TAG, "getAlarms : " + getAlarms);  // -> getAlarms : /storage/emulated/0/Alarms
        /**
         * Environment.DIRECTORY_ALARMS 알람 오디오 파일 폴더
         * Environment.DIRECTORY_DCIM 카메라로 촬영한 사진 폴더
         * Environment.DIRECTORY_DOWNLOADS 다운로드 파일 폴더
         * Environment.DIRECTORY_MUSIC 음악 파일 폴더
         * Environment.DIRECTORY_MOVIES 영상 파일 폴더
         * Environment.DIRECTORY_NOTIFICATIONS 알람음으로 사용할 오디오 파일 폴더
         * Environment.DIRECTORY_PICTURES 이미지 파일 폴더
         * Environment.DIRECTORY_PODCASTS 팟캐스트 파일 폴더
         */

        return getCache2;
    }
}
