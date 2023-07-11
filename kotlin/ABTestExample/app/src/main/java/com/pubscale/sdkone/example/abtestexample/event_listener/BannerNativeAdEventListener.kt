package com.pubscale.sdkone.example.abtestexample.event_listener

interface BannerNativeAdEventListener {
    fun onAdLoading()
    fun onAdLoaded()
    fun onAdLoadFailed()
    fun onUiiOpened()
    fun onUiiClosed()
    fun onReadyForRefresh()
}