package com.vijayyogapp.interfaces;

public interface RESTClientResponse {
    void onSuccess(Object response, int statusCode);
    void onFailure(Object errorResponse);
}
