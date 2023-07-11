package com.pubscale.sdkone.example.abtestexample.event_listener

interface InterstitialAdEventListener {
    fun onAdLoading()
    fun onAdLoaded()
    fun onAdLoadFailed()
    fun onAdShowFailed()
    fun onAdOpened()
    fun onAdClosed()
}