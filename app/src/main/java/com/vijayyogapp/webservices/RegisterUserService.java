package com.vijayyogapp.webservices;

/**
 * Created by SUHAS on 10/03/2017.
 */


import android.app.Activity;

import com.google.gson.Gson;
import com.vijayyogapp.interfaces.RESTClientResponse;
import com.vijayyogapp.models.RegisterResponseModel;
import com.vijayyogapp.models.UserData;
import com.vijayyogapp.utils.Utils;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class RegisterUserService {

    public void registerUser(final Activity activity, UserData userData, final RESTClientResponse restClientResponse) {

        Call<RegisterResponseModel> call = Utils.getInstance().getRestClient().registerUser(userData);
        call.enqueue(new Callback<RegisterResponseModel>() {
            @Override
            public void onResponse(Call<RegisterResponseModel> call, Response<RegisterResponseModel> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            RegisterResponseModel RegisterResponseModel = new Gson().getAdapter(RegisterResponseModel.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(RegisterResponseModel, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponseModel> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
