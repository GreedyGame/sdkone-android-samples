package com.pubscale.sdkone.example.abtestexample.event_listener;

public interface RewardedAdEventListener {
    void onReward();
    void onAdLoading();
    void onAdLoaded();
    void onAdLoadFailed();
    void onAdShowFailed();
    void onAdOpened();
    void onAdClosed();
}
