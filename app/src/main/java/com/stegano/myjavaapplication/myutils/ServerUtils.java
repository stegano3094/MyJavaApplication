package com.stegano.myjavaapplication.myutils;

import okhttp3.FormBody;

public class ServerUtils {
    // 모든 기능의 기본이 되는 주소
    public static final String BASE_URL = "http://54.180.52.26";

    // 로그인 하는 기능
    public void postRequestLogin(String email, String pw) {
        // 서버에 입력받은 이메일과 비밀번호를 로그인 기능(POST/user)로 전달
        // OkHttp를 사용
        String urlString = BASE_URL + "/user";
//        String formData = FormBody.Builder;
    }
}
