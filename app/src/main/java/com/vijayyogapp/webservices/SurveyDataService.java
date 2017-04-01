package com.vijayyogapp.webservices;
import android.app.Activity;

import com.google.gson.Gson;
import com.vijayyogapp.interfaces.RESTClientResponse;
import com.vijayyogapp.models.SurveyDataRequestModel;
import com.vijayyogapp.models.SurveyDataResponse;
import com.vijayyogapp.models.VoterDataRequestModel;
import com.vijayyogapp.models.SurveyDataResponse;
import com.vijayyogapp.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by suhas.bachewar on 1/24/2017.
 */
public class SurveyDataService {

    public void getSurveyData(final Activity activity, SurveyDataRequestModel requestModel, final RESTClientResponse restClientResponse) {

        Call<SurveyDataResponse> call = Utils.getInstance().getRestClient().getSurveyData(requestModel);
        call.enqueue(new Callback<SurveyDataResponse>() {
            @Override
            public void onResponse(Call<SurveyDataResponse> call, Response<SurveyDataResponse> response) {

                if (response.isSuccessful()) {
                    restClientResponse.onSuccess(response.body(), response.code());
                } else {
                    if (response.errorBody() != null) {
                        try {
                            SurveyDataResponse SurveyDataResponse = new Gson().getAdapter(SurveyDataResponse.class)
                                    .fromJson(response.errorBody().string());
                            restClientResponse.onSuccess(SurveyDataResponse, response.code());
                        } catch (Exception e) {
                            e.printStackTrace();
                            restClientResponse.onFailure(e);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<SurveyDataResponse> call, Throwable t) {
                t.printStackTrace();
                restClientResponse.onFailure(t);
            }
        });
    }

}
