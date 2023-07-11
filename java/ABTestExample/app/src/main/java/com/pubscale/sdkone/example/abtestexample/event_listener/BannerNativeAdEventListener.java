package com.pubscale.sdkone.example.abtestexample.event_listener;

public interface BannerNativeAdEventListener {
    void onAdLoading();
    void onAdLoaded();
    void onAdLoadFailed();
    void onUiiOpened();
    void onUiiClosed();
    void onReadyForRefresh();
}
