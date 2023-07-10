package com.pubscale.sdkone.example.abtestexample.event_listener

interface AppOpenAdEventListener {
    fun onAdLoading()
    fun onAdLoaded()
    fun onAdLoadFailed()
    fun onAdShowFailed()
    fun onAdOpened()
    fun onAdClosed()
}