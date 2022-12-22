package com.example.apitask;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitAPI {

    @POST("Shops")
    Call<DataModal> createPost(@Body DataModal dataModal);

    @DELETE("Shops/{id}")
    Call<Void> deleteData(@Path("id") int id);
}
