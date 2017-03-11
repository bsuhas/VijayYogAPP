package com.vijayyogapp.interfaces;

import com.vijayyogapp.models.BoothDataResponse;
import com.vijayyogapp.models.RegisterResponseModel;
import com.vijayyogapp.models.RequestModel;
import com.vijayyogapp.models.UserData;
import com.vijayyogapp.models.VoterDataResponseModel;

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

    @Headers("Content-Type: application/json")
    @POST("BoothAddress")
    Call<BoothDataResponse> getBoothData(@Body RequestModel model);

    @Headers("Content-Type: application/json")
    @POST("VoterData")
    Call<VoterDataResponseModel> getVoterData(@Body RequestModel model);
}
