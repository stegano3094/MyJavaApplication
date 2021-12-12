package com.stegano.myjavaapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class HTTPActivity extends AppCompatActivity {
    static final String TAG = "HTTPActivity";

    EditText editText;
    TextView textView;

    // HttpURLConnection에 사용됨
    Handler handler = new Handler();

    // Volley에 사용됨
    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httpactivity);

        editText = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);

        // 검색할 때 http:// 붙여주어야함, 귀찮아서 미리 주소 적어놓음
        editText.setText("https://m.naver.com");

        // HttpURLConnection 사용
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String urlStr = editText.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        request(urlStr);
                    }
                }).start();
            }
        });

        // Volley 사용 (gradle에 implementation 'com.android.volley:volley:1.2.1' 추가해주어야함)
        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeRequest();
            }
        });

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());  // RequestQueue 객체 생성
        }
    }

    public void request(String urlStr) {
        StringBuilder output = new StringBuilder();

        try {
            URL url = new URL(urlStr);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            if (conn != null) {
                conn.setConnectTimeout(10000);  // 1000 : 1초
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                int resCode = conn.getResponseCode();
                Log.d(TAG, "resCode : " + resCode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line = null;

                while (true) {
                    line = reader.readLine();

                    if (line == null) {
                        break;
                    }

                    output.append(line + "\n");
                }
                Log.d(TAG, "output : " + output);

                reader.close();
                conn.disconnect();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


        handler.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("");  // 출력될 문구 초기화

                textView.append("응답 -> " + output.toString() + "\n");
            }
        });
    }

    public void makeRequest() {
        textView.setText("");  // 출력될 문구 초기화

        String url = editText.getText().toString();

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.append("응답 -> " + response + "\n");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        textView.append("에러 -> " + error.getMessage() + "\n");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
        textView.append("요청 보냄\n");
    }
}