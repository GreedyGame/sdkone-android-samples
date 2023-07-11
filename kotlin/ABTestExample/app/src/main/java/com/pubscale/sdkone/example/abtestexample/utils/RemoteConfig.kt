package com.pubscale.sdkone.example.abtestexample.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

object RemoteConfig {
    private var mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    init {
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(600)
            .build()
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings)
        val defaults: MutableMap<String, Any> = HashMap()
        defaults["ad_provider"] = "sdkone"
        mFirebaseRemoteConfig.setDefaultsAsync(defaults)
        mFirebaseRemoteConfig.fetchAndActivate()
    }

    val adProvider: String
        get() = mFirebaseRemoteConfig.getString("ad_provider")
}