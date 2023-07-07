package com.pubscale.sdkone.example.abtestexample.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static SharedPref sharedPref = null;
    private SharedPreferences sharedPreferences;

    private SharedPref(Context context) {
        sharedPreferences = context.getSharedPreferences("abtestsample", Context.MODE_PRIVATE);
    }

    public static synchronized void initialize(Context context) {
        if(sharedPref != null) return;
        sharedPref = new SharedPref(context);
    }

    public static SharedPref getInstance()  {
        return sharedPref;
    }

    public boolean isAppOpenAdDisabled() {
        return sharedPreferences.getBoolean("app_open_ads_disabled" ,false);
    }

    public void disableAppOpenAd(boolean value) {
        sharedPreferences.edit().putBoolean("app_open_ads_disabled", value).apply();
    }
}
