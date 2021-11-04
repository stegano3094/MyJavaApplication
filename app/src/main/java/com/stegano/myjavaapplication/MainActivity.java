package com.stegano.myjavaapplication;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.stegano.myjavaapplication.api.API_RetrofitService;
import com.stegano.myjavaapplication.datas.AppHelper;
import com.stegano.myjavaapplication.dto.PostData;
import com.stegano.myjavaapplication.dto.UserDTO;
import com.stegano.myjavaapplication.staticclass.RetrofitFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    public Button loginBtn;
    public EditText emailEdt;
    public EditText passwordEdt;
    public TextView resultTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        String url = "https://www.google.co.kr";
        sendRequest(url);

        // 파이어베이스에서 현재 토큰 가져오기
        FirebaseMessaging.getInstance().getToken()
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
                        Toast.makeText(MainActivity.this, "토큰 : " + token, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    void setupEvents() {
        loginBtn = (Button) findViewById(R.id.loginBtn);
        emailEdt = (EditText) findViewById(R.id.emailEdt);
        passwordEdt = (EditText) findViewById(R.id.passwordEdt);

        resultTxt = (TextView) findViewById(R.id.resultTxt);
        resultTxt.setMovementMethod(new ScrollingMovementMethod());
        resultTxt.getOverScrollMode();

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

                List<PostData> postData = response.body();
                for(PostData post : postData) {
                    String content = "";
                    content += "ID: " + post.getId() + "\n";
                    content += "USER ID: " + post.getUserId() + "\n";
                    content += "TITLE: " + post.getTitle() + "\n";
                    content += "TEXT: " + post.getText() + "\n";

                    resultTxt.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<PostData>> call, Throwable t) {
                resultTxt.setText(t.getMessage());
            }
        });


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


    public void sendRequest(String url) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url,
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

        stringRequest.setShouldCache(false);  // 이전 결과가 있더라도 새로 요청
        AppHelper.requestQueue = Volley.newRequestQueue(this);
        AppHelper.requestQueue.add(stringRequest);
    }
}