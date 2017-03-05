package com.vijayyogapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SUHAS on 07/05/2016.
 */
public class  UserPreferences {

    private final String WOM_PREFERENCE = "container_user_preference";

    private SharedPreferences mSharedPreferences;

    private static UserPreferences mInstance;

    public static UserPreferences getInstance(Context context) {
        if(mInstance==null)
            mInstance = new UserPreferences(context);

        return mInstance;
    }

    private UserPreferences(Context context) {
        mSharedPreferences = context.getSharedPreferences(WOM_PREFERENCE, Context.MODE_PRIVATE);
    }

    public void clearData()
    {
        mSharedPreferences.edit().clear().commit();
    }

    private void saveString(String key, String value)
    {
        if(mSharedPreferences==null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    private void saveInt(String key, int value)
    {
        if(mSharedPreferences==null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    private void saveBoolean(String key, boolean value)
    {
        if(mSharedPreferences==null)
            return;

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    private String loadString(String key, String defaultValue) {
        String data = mSharedPreferences.getString(key, defaultValue);
        return data;
    }

    private int loadInt(String key, int defaultValue) {
        int data = mSharedPreferences.getInt(key, defaultValue);
        return data;
    }

    private boolean loadBoolean(String key, boolean defaultValue) {
        boolean data = mSharedPreferences.getBoolean(key, defaultValue);
        return data;
    }

    public void saveUserInfo(String username, boolean keepLogin)
    {
        saveString(Constants.USERNAME, username);
        saveBoolean(Constants.KEEP_ME_LOGIN, keepLogin);
    }
    public void saveUserAuthKey(String authkey)
    {
        saveString(Constants.AUTHkEY, authkey);
    }
    public void setCustomerCodeApiCalled(boolean result)
    {
        saveBoolean(Constants.CUSTOMER_CODE_API_CALLED, result);
    }
    public void setDashboardListView(boolean result)
    {
        saveBoolean(Constants.DASHBOARD_VIEW, result);
    }

    public boolean isCustomerCodeApiCalled() {
        return loadBoolean(Constants.CUSTOMER_CODE_API_CALLED, false);
    }
    public boolean isDashboardListview() {
        return loadBoolean(Constants.DASHBOARD_VIEW, false);
    }

    //check whether user is login or not
    public boolean isUserLogin() {
        return loadBoolean(Constants.KEEP_ME_LOGIN, false);
    }
    public String getUserName()
    {
        return (loadString(Constants.USERNAME, ""));
    }
    public String getAuthKey()
    {
        return (loadString(Constants.AUTHkEY, ""));
    }
}
