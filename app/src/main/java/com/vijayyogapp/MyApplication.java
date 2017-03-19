package com.vijayyogapp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.vijayyogapp.utils.Constants;
import com.vijayyogapp.utils.DialogUtils;
import com.vijayyogapp.utils.UserPreferences;

/**
 * Created by SUHAS on 09/03/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        String lang = UserPreferences.getInstance(this).getUserDefaultLanguage();
        DialogUtils.updateConfig(MyApplication.this, lang);
    }
}
