package com.example.apitask;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitAPI {

    @POST("Shops")
    Call<DataModal> createPost(@Body DataModal dataModal);
}
