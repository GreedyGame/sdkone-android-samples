package com.pubscale.sdkone.example.abtestexample.ad_formats

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.pubscale.sdkone.core.app_open_ads.general.AppOpenAdsEventsListener
import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.example.abtestexample.event_listener.AppOpenAdEventListener
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig

object AppOpen {

    private val remoteConfig = RemoteConfig
    private var isAppOpenAdDisabled = false
    private var admobAppOpenAd: AppOpenAd? = null


    private var eventListener: AppOpenAdEventListener = object : AppOpenAdEventListener {
        override fun onAdLoading() {}
        override fun onAdLoaded() {}
        override fun onAdLoadFailed() {}
        override fun onAdShowFailed() {}
        override fun onAdOpened() {}
        override fun onAdClosed() {}
        override fun onAdDisabled() {}
    }

    init {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false)
    }

    fun setAppOpenAdEventListener(eventListener: AppOpenAdEventListener): AppOpen {
        this.eventListener = eventListener
        return this
    }

    fun disableAppOpenAd(value: Boolean) {
        isAppOpenAdDisabled = value
    }

    fun loadAd(context: Context) {
        if (isAppOpenAdDisabled) {
            eventListener.onAdDisabled()
            return
        }
        eventListener.onAdLoading()
        when (remoteConfig.adProvider) {
            "admob" -> {
                loadAdmobAppOpenAd(context)
            }

            "sdkone" -> {
                loadSdkoneAppOpenAd()
            }

            else -> {
                eventListener.onAdLoadFailed()
            }
        }
    }

    fun showAd(activity: Activity) {
        when (remoteConfig.adProvider) {
            "admob" -> {
                showAdmobAppOpenAd(activity)
            }

            "sdkone" -> {
                GGAppOpenAds.show(activity)
            }

            else -> {}
        }
    }

    private fun loadAdmobAppOpenAd(context: Context) {
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context, "ca-app-pub-3940256099942544/3419835294", request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    eventListener.onAdLoaded()
                    admobAppOpenAd = ad
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    eventListener.onAdLoadFailed()
                }
            })
    }

    private fun showAdmobAppOpenAd(activity: Activity) {
        if (admobAppOpenAd == null) {
            eventListener.onAdShowFailed()
            return
        }
        admobAppOpenAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent()
                eventListener.onAdClosed()
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                super.onAdFailedToShowFullScreenContent(adError)
                eventListener.onAdShowFailed()
            }

            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
                eventListener.onAdOpened()
            }
        }
        admobAppOpenAd?.show(activity)
    }

    private fun loadSdkoneAppOpenAd() {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(true)
        GGAppOpenAds.addListener(object : AppOpenAdsEventsListener {
            override fun onAdLoaded() {
                eventListener.onAdLoaded()
            }

            override fun onAdLoadFailed(adErrors: AdErrors) {
                eventListener.onAdLoadFailed()
                GGAppOpenAds.removeListener(this)
            }

            override fun onAdShowFailed() {
                eventListener.onAdShowFailed()
                GGAppOpenAds.removeListener(this)
            }

            override fun onAdOpened() {
                eventListener.onAdOpened()
            }

            override fun onAdClosed() {
                eventListener.onAdClosed()
                GGAppOpenAds.removeListener(this)
            }
        })
        GGAppOpenAds.loadAd("float-13570")
    }
}