package com.pubscale.sdkone.example.abtestexample.utils;

import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import java.util.HashMap;
import java.util.Map;

public class RemoteConfig {
    private static RemoteConfig remoteConfig = null;
    private FirebaseRemoteConfig mFirebaseRemoteConfig;

    private RemoteConfig() {
        mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .build();
        mFirebaseRemoteConfig.setConfigSettingsAsync(configSettings);
        Map<String, Object> defaults = new HashMap<>();
        defaults.put("ad_provider", "sdkone");
        mFirebaseRemoteConfig.setDefaultsAsync(defaults);
    }

    public static synchronized RemoteConfig getInstance() {
        if(remoteConfig != null) return remoteConfig;
        remoteConfig = new RemoteConfig();
        return remoteConfig;
    }

    public String adProvider() {
        return mFirebaseRemoteConfig.getString("ad_provider");
    }
}
