package com.codemountain.shoppadi.utils;

import android.content.SharedPreferences;

import com.codemountain.shoppadi.ShopPadi;

public class SharedPref {
    private static SharedPref instance;

    public SharedPref() {
    }

    public static SharedPref init() {
        if (instance == null){
            instance = new SharedPref();
        }
        return instance;
    }

    public void putStringInPref(String key, String value) {
        SharedPreferences.Editor editor = ShopPadi.getSharedPref().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void putIntInPref(String key, int value) {
        SharedPreferences.Editor editor = ShopPadi.getSharedPref().edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void putBooleanInPref(String key, boolean value) {
        SharedPreferences.Editor editor = ShopPadi.getSharedPref().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
