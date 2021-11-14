package com.stegano.myjavaapplication;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadActivity extends AppCompatActivity {
    private static final String TAG = "ThreadActivity";

    Button threadBtn;
    Button runnableBtn;
    Button asyncTaskBtn;
    Button timerBtn;

    static TextView resultTxt;
    private static int count = 0;

    private static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mActivity = this;

        threadBtn = findViewById(R.id.threadBtn);
        runnableBtn = findViewById(R.id.runnableBtn);
        asyncTaskBtn = findViewById(R.id.asyncTaskBtn);
        timerBtn = findViewById(R.id.timerBtn);

        resultTxt = findViewById(R.id.resultTxt);

        threadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 같은 Thread 객체가 start()를 두 번 이상 호출하면 앱이 종료됨.
                // 반드시 if(thread == null) 인지 확인하고 돌리길
                Thread thread = new MyThread();  // Thread 상속
                thread.start();
            }
        });

        runnableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new MyRunnable());  // 기본 스레드에 Runnable 이용
                thread.start();
            }
        });

        asyncTaskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AsyncTask 클래스는 반드시 UI 스레드에서 로드해야 된다.
                // AsyncTask 인스턴스는 반드시 UI 스레드에서 생성되어야 한다.
                // execute()도 반드시 UI 스레드에서 호출해야 한다.
                // 모든 콜백들은 직접 호출하면 안된다.
                // 태스크는 한번만 실행할 수 있다. (execute() 연속으로 쓰지말라는 의미)
                // AsyncTask는 동시에 실행이 아니라 순차적 실행한다.
                new MyAsyncTask().execute(0);  // 파라미터값으로 0을 넘겨주면서 AsyncTask를 실행함. 0, 1, 2 이렇게 넣을 수도 있음.

                // 순차적 수행
//                new MyAsyncTask().execute(0);
//                new MyAsyncTask().execute(0);
//                new MyAsyncTask().execute(0);

                // 병렬 수행
//                new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
//                new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);
//                new MyAsyncTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, 0);

                // 실행 중 중지
//                MyAsyncTask myAsyncTask = new MyAsyncTask();
//                myAsyncTask.execute(0);
//                myAsyncTask.cancel(true);  // 실행중 중지 가능 -> onCancelled()가 호출됨
            }
        });

        timerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = 0;

                Timer timer = new Timer();  // 타이머 객체 생성
                TimerTask timerTask = new TimerTask() {  // TimerTask에서 할 일 구현
                    @Override
                    public void run() {  // 반복실행할 구문
                        count++;

                        // TimerTask도 UI 건드리면 안되므로 UI 스레드에서 따로 건드린다.
                        mActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                resultTxt.setText("MyTimer i : " + count);
                            }
                        });

                        if(count >= 10) {
//                            timer.cancel();  // TimerTask 안에 cancel()을 두어 해당 조건에 만족하면 TimerTask를 중지함
                        }
                    }
                };

                timer.schedule(timerTask, 0, 1000); // Timer 실행. 스케줄러가 1초마다 timerTask를 시작함
            }
        });

    }
    // onCreate end --------------------------------------------------------------------------------


    // Thread --------------------------------------------------------------------------------------
    public static class MyThread extends Thread {
        @Override
        public void run() {  // 오래 걸리는 작업을 Thread에서 처리함
            super.run();

            for (int i = 0; i <= 30; i++) {
                try {
                    Log.d(TAG, "MyThread i : " + i);
//                    resultTxt.setText(i);  // 스레드에서 ui 작업 시 오류 발생
                    // 스레드에서 핸들러 생성 시 오류 발생

                    // 스레드 안에서 ui를 작업하고 싶을 때
                    // 방법 1. runOnUiThread()를 사용 (Activity가 제공하는 메서드이다. fragment에서는 안됨)
                    count = i;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultTxt.setText("MyThread count : " + count);
                        }
                    });

                    // 방법 2. handler를 구현해서 사용하는 방법 (스레드에서 핸들러 생성 시 오류 발생)
                    handler.post(new Runnable() {  // post()는 바로 실행, postDelayed()는 지연시간 후에 실행
                        @Override
                        public void run() {
                            resultTxt.append("\nhandler 왔어용 count : " + count);
                        }
                    });

                    // handler 구현 방법 2
                    handler2.sendEmptyMessage(i);  // sendEmptyMessage()를 사용하면 handleMessage()가 호출됨

                    // 방법 3. 뷰의 handler를 구현해서 사용하는 방법 (모든 View는 내부에 Handler를 가지고 있다.)
                    resultTxt.post(new Runnable() {
                        @Override
                        public void run() {
                            resultTxt.append("\nView 왔어용 count : " + count);
                        }
                    });

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "MyThread finished");
        }
    }

    private static Handler handler = new Handler();  // 핸들러 객체 생성

    private static Handler handler2 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            Log.d(TAG, "MyThread handler2 msg.what : " + msg.what);
            resultTxt.append("\nhandler2 왔어용 msg.what : " + msg.what);
//            Toast.makeText(mActivity.getApplicationContext(), "msg.what : " + msg.what, Toast.LENGTH_SHORT).show();
        }
    };

    // Runnable ------------------------------------------------------------------------------------
    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i <= 30; i++) {
                try {
                    Log.d(TAG, "MyRunnable i : " + i);

                    count = i;
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultTxt.setText("MyRunnable count : " + count);
                        }
                    });

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Log.d(TAG, "MyRunnable finished");
        }
    }

    // AsyncTask<초기값전달, 프로그레스타입, 결과타입> ---------------------------------------------------
    private static class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected void onPreExecute() {  // 호출되면 최초로 실행되는 부분
            Toast.makeText(mActivity.getApplicationContext(), "AsyncTask", Toast.LENGTH_SHORT).show();
        }

        // 여기 있는 어노테이션 @WorkerThread, @UiThread는 아무 기능이 없고 확인하기 편도록 붙여놓은 것임
        // AsyncTask에서 여기만 WorkerThread이고 나머지는 UiThread로 동작함
        // 꼭 필요한 부분은 doInBackground() 이 부분이고 나머지는 없어도 동작함
        @WorkerThread
        @Override
        protected Integer doInBackground(Integer... params) {  // AsyncTask<Integer, Integer, String>의 1번째 타입과 일치
            // 오래 걸리는 작업 처리. doInBackground()는 스레드로 동작함
            int number = params[0];

            for (int i = 0; i < 30; i++ ) {
                number++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // ui갱신
                publishProgress(number);  // publishProgress()를 사용하면 onProgressUpdate()가 호출된다.
            }

            return number;  // return이 수행되면 onPostExecute()가 호출된다.
        }

        @Override
        protected void onProgressUpdate(Integer... values) {  // AsyncTask<Integer, Integer, String>의 2번째 타입과 일치
            // ui 갱신
            resultTxt.setText("AsyncTask count : " + values[0]);
        }

        @Override
        protected void onPostExecute(Integer s) {
            Toast.makeText(mActivity.getApplicationContext(), "AsyncTask 작업 완료", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled(Integer integer) {
            // 취소 처리
        }
    }
}