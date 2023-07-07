package com.pubscale.sdkone.example.abtestexample;

import android.app.Application;

import com.google.android.gms.ads.MobileAds;
import com.google.firebase.FirebaseApp;
import com.pubscale.sdkone.example.abtestexample.utils.SharedPref;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        MobileAds.initialize(this);
        SharedPref.initialize(this);
    }
}
