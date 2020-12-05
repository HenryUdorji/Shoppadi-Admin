package com.codemountain.shoppadi;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.codemountain.shoppadi.utils.SharedPref;

public class ShopPadi extends Application {
    private Context context;
    private static SharedPreferences sharedPref;

    public static SharedPreferences getSharedPref() {
        return sharedPref;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPref.init();
        context = getApplicationContext();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
    }
}
