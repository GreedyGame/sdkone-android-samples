package com.pubscale.sdkone.example.abtestexample.event_listener

interface RewardedAdEventListener {
    fun onReward()
    fun onAdLoading()
    fun onAdLoaded()
    fun onAdLoadFailed()
    fun onAdShowFailed()
    fun onAdOpened()
    fun onAdClosed()
}