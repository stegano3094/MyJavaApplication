package com.stegano.myjavaapplication.api;

import com.stegano.myjavaapplication.dto.PostData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_RetrofitService {
    @GET("posts")
    Call<List<PostData>> getPostDatas();
}
