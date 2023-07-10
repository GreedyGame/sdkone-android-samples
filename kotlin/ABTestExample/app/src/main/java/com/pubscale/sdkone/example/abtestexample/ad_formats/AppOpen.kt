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

    private var admobAppOpenAd: AppOpenAd? = null

    var isLoadingAd: Boolean = false
        private set
    var isShowingAd: Boolean = false
        private set

    val isAdAvailable: Boolean
        get() = admobAppOpenAd != null

    private var eventListener: AppOpenAdEventListener = object : AppOpenAdEventListener {
        override fun onAdLoading() {}
        override fun onAdLoaded() {}
        override fun onAdLoadFailed() {}
        override fun onAdShowFailed() {}
        override fun onAdOpened() {}
        override fun onAdClosed() {}
    }

    init {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false)
    }

    fun setAppOpenAdEventListener(eventListener: AppOpenAdEventListener): AppOpen {
        this.eventListener = eventListener
        return this
    }

    fun loadAd(context: Context, manual: Boolean) {
        eventListener.onAdLoading()
        when (RemoteConfig.adProvider) {
            "admob" -> {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(false)
                loadAdmobAppOpenAd(context)
            }

            "sdkone" -> {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(true)
                if(manual) { loadSdkoneAppOpenAd() }
                else { /** SDKOne will handle auto app open ads upon app coming to foreground */}
            }

            else -> {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(false)
                eventListener.onAdLoadFailed()
            }
        }
    }

    fun showAd(activity: Activity, manual: Boolean) {
        when (RemoteConfig.adProvider) {
            "admob" -> {
                showAdmobAppOpenAd(activity)
            }

            "sdkone" -> {
                if(manual) { GGAppOpenAds.show(activity) }
                else { /** SDKOne will handle auto app open ads upon app coming to foreground */}
            }

            else -> {
                eventListener.onAdShowFailed()
            }
        }
    }

    private fun loadAdmobAppOpenAd(context: Context) {
        isLoadingAd = true
        val request = AdRequest.Builder().build()
        AppOpenAd.load(
            context, "ca-app-pub-3940256099942544/3419835294", request,
            object : AppOpenAd.AppOpenAdLoadCallback() {
                override fun onAdLoaded(ad: AppOpenAd) {
                    admobAppOpenAd = ad
                    isLoadingAd = false
                    eventListener.onAdLoaded()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    isLoadingAd = false
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
                isShowingAd = false
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
                isShowingAd = true
                eventListener.onAdOpened()
            }
        }
        admobAppOpenAd?.show(activity)
    }

    private fun loadSdkoneAppOpenAd() {
        isLoadingAd = true
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(true)
        GGAppOpenAds.addListener(object : AppOpenAdsEventsListener {
            override fun onAdLoaded() {
                isLoadingAd = false
                eventListener.onAdLoaded()
            }

            override fun onAdLoadFailed(adErrors: AdErrors) {
                isLoadingAd = false
                eventListener.onAdLoadFailed()
                GGAppOpenAds.removeListener(this)
            }

            override fun onAdShowFailed() {
                isShowingAd = false
                eventListener.onAdShowFailed()
                GGAppOpenAds.removeListener(this)
            }

            override fun onAdOpened() {
                isShowingAd = true
                eventListener.onAdOpened()
            }

            override fun onAdClosed() {
                isShowingAd = false
                eventListener.onAdClosed()
                GGAppOpenAds.removeListener(this)
            }
        })
        GGAppOpenAds.loadAd("float-13570")
    }
}