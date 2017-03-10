package com.vijayyogapp;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by SUHAS on 09/03/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
