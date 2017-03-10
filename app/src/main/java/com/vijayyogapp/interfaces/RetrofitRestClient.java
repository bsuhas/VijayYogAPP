package com.vijayyogapp.interfaces;

import com.vijayyogapp.models.RegisterResponseModel;
import com.vijayyogapp.models.UserData;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by SUHAS on 10/03/2017.
 */

public interface RetrofitRestClient {
    @Headers("Content-Type: application/json")
    @POST("register")
    Call<RegisterResponseModel> registerUser(@Body UserData user);
}
