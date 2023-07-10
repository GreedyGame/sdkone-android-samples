package com.pubscale.sdkone.example.abtestexample

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.firebase.FirebaseApp
import com.pubscale.sdkone.example.abtestexample.utils.AppOpenAdManager

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        MobileAds.initialize(this)
        registerActivityLifecycleCallbacks(AppOpenAdManager())
    }

}