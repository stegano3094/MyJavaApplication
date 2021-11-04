package com.stegano.myjavaapplication.staticclass;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static Retrofit createRetrofit(String baseUrl) { //Retrofit 객체를 생성하는 메서드를 선언해두고 재사용
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
