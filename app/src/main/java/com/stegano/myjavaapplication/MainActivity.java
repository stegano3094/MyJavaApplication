package com.stegano.myjavaapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.stegano.myjavaapplication.api.API_RetrofitService;
import com.stegano.myjavaapplication.datas.AppHelper;
import com.stegano.myjavaapplication.dto.PostData;
import com.stegano.myjavaapplication.dto.UserDTO;
import com.stegano.myjavaapplication.staticclass.RetrofitFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    public Button nextActivityBtn;
    public Button nextActivityBtn2;
    public Button nextActivityBtn3;
    public Button nextActivityBtn4;
    public Button nextActivityBtn5;
    public Button nextActivityBtn6;
    public Button nextActivityBtn7;
    public Button nextActivityBtn8;
    public Button nextActivityBtn9;
    public Button nextActivityBtn10;
    public Button nextActivityBtn11;
    public Button nextActivityBtn12;
    public Button nextActivityBtn13;
    public Button nextActivityBtn14;
    public Button nextActivityBtn15;
    public Button nextActivityBtn16;
    public Button nextActivityBtn17;
    public Button nextActivityBtn18;
    public Button nextActivityBtn19;
    public Button nextActivityBtn20;

    public TextView resultTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  // 화면켜짐 유지

        Log.d(TAG, "onCreate() getWindow().addFlags()");

        setupEvents();
        setValues();

        // testing
        String s = "test123456789";
        Log.d("MainActivity", "onCreate : " + s.substring(2));
        Log.d("MainActivity", "onCreate : " + s.substring(2, 4));
        Log.d("MainActivity", "onCreate : " + s.charAt(2));

        if(AppHelper.requestQueue != null) {
            AppHelper.requestQueue = Volley.newRequestQueue(mContext);  // 큐 생성
        }
    }

    @Override
    void setupEvents() {
        // 일정 시간 후에 화면 꺼지도록 설정
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                        Log.d(TAG, "onCreate() timerTask getWindow().clearFlags()");
                    }
                });
            }
        };
        timer.schedule(timerTask, 30000);  // 300초 뒤 실행 -> 화면 꺼짐 가능

        // nextActivityBtn 버튼 클릭 시 TabLayoutAndViewPagerActivity 화면으로 이동
        nextActivityBtn = findViewById(R.id.nextActivityBtn);
        nextActivityBtn.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, TestActivity.class);
            startActivity(intent);
        });

        nextActivityBtn2 = findViewById(R.id.nextActivityBtn2);
        nextActivityBtn2.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, TabLayoutAndViewPagerActivity.class);
            startActivity(intent);
        });

        nextActivityBtn3 = findViewById(R.id.nextActivityBtn3);
        nextActivityBtn3.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ForegroundServiceActivity.class);
            startActivity(intent);
        });

        nextActivityBtn4 = findViewById(R.id.nextActivityBtn4);
        nextActivityBtn4.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, NotificationActivity.class);
            startActivity(intent);
        });

        nextActivityBtn5 = findViewById(R.id.nextActivityBtn5);
        nextActivityBtn5.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, RecyclerViewActivity.class);
            startActivity(intent);
        });

        nextActivityBtn6 = findViewById(R.id.nextActivityBtn6);
        nextActivityBtn6.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, GoogleMapActivity.class);
            startActivity(intent);
        });

        nextActivityBtn7 = findViewById(R.id.nextActivityBtn7);
        nextActivityBtn7.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ThreadActivity.class);
            startActivity(intent);
        });

        nextActivityBtn8 = findViewById(R.id.nextActivityBtn8);
        nextActivityBtn8.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, ReceiverActivity.class);
            startActivity(intent);
        });

        nextActivityBtn9 = findViewById(R.id.nextActivityBtn9);
        nextActivityBtn9.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, SocketActivity.class);
            startActivity(intent);
        });

        nextActivityBtn10 = findViewById(R.id.nextActivityBtn10);
        nextActivityBtn10.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, HTTPActivity.class);
            startActivity(intent);
        });

        nextActivityBtn11 = findViewById(R.id.nextActivityBtn11);
        nextActivityBtn11.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, LocationActivity.class);
            startActivity(intent);
        });

        // 레트로핏을 이용하여 텍스트뷰에 결과 넣기
        resultTxt = (TextView) findViewById(R.id.resultTxt);
        Retrofit retrofit = RetrofitFactory.createRetrofit("https://jsonplaceholder.typicode.com/");
        API_RetrofitService api_retrofitService = retrofit.create(API_RetrofitService.class);
        Call<List<PostData>> call = api_retrofitService.getPostDatas();
        call.enqueue(new Callback<List<PostData>>() {
            @Override
            public void onResponse(Call<List<PostData>> call, retrofit2.Response<List<PostData>> response) {
                if (!response.isSuccessful()) {
                    resultTxt.setText("code: " + response.code());
                    return;
                }

                resultTxt.setText("");  // 기존에 있던 문자는 제거
                List<PostData> postData = response.body();
                for(PostData post : postData) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "USER ID: " + post.getUserId() + "\n";
                    content += "TITLE: " + post.getTitle() + "\n";
                    content += "TEXT: " + post.getText() + "\n\n";

                    resultTxt.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<PostData>> call, Throwable t) {
                resultTxt.setText(t.getMessage());
            }
        });
    }

    @Override
    void setValues() {
        // Gson 사용방법 (dto/UserDTO 파일 참조)
        String json = "{\"name\":\"식빵\", \"age\":31, \"city\":\"New York\"}";
        Gson gson = new Gson();
        UserDTO userDTO = gson.fromJson(json, UserDTO.class);

        if (BuildConfig.DEBUG) {
            Log.d("MainActivity", userDTO.getCity());
            Log.d("MainActivity", userDTO.getName());
            Log.d("MainActivity", "" + userDTO.getAge());
        }

        String url = "https://www.google.co.kr";
        sendRequest(url);
    }

    // GET 방식으로 API 가져오기
    public void sendRequest(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("API_SendRequest", "onResponse : " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("API_SendRequest", "onErrorResponse : " + error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
//                params.put("key", "value");  // 끝에 { }를 추가하여 요청 보낼 떄 포함시킬 파라미터
                return params;
            }
        };

        // 보내기 (결과는 onResponse()와 onErrorResponse()로 반환됨)
        stringRequest.setShouldCache(false);  // 이전 결과가 있더라도 새로 요청
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(stringRequest);
    }

    // 화면 회전 시 데이터 유지
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