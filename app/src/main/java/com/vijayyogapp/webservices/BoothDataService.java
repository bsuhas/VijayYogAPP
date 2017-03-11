package com.vijayyogapp.webservices;

/**
 * Created by SUHAS on 10/03/2017.
 */


import android.app.Activity;

import com.google.gson.Gson;
import com.vijayyogapp.interfaces.RESTClientResponse;
import com.vijayyogapp.models.BoothDataResponse;
import com.vijayyogapp.models.RequestModel;
import com.vijayyogapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class BoothDataService {

    public void getBoothData(final Activity activity, RequestModel requestModel, final RESTClientResponse restClientResponse) {

        Call<BoothDataResponse> call = Utils.getInstance().getRestClient().getBoothData(requestModel);
        call.enqueue(new Callback<BoothDataResponse>() {
            @Override
            public void onResponse(Call<BoothDataResponse> call, Response<BoothDataResponse> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            BoothDataResponse BoothDataResponse = new Gson().getAdapter(BoothDataResponse.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(BoothDataResponse, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<BoothDataResponse> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
