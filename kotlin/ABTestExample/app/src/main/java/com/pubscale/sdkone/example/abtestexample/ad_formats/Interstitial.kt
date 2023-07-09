package com.pubscale.sdkone.example.abtestexample.ad_formats

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.pubscale.sdkone.core.interstitial.general.GGInterstitialAd
import com.pubscale.sdkone.core.interstitial.general.GGInterstitialEventsListener
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.example.abtestexample.event_listener.InterstitialAdEventListener
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig

object Interstitial {
    private var admobInterstitialAd: InterstitialAd? = null
    private var ggInterstitialAd: GGInterstitialAd? = null

    private var eventListener: InterstitialAdEventListener = object : InterstitialAdEventListener {
        override fun onAdLoading() {}
        override fun onAdLoaded() {}
        override fun onAdLoadFailed() {}
        override fun onAdShowFailed() {}
        override fun onAdOpened() {}
        override fun onAdClosed() {}
    }

    fun setInterstitialAdEventListener(eventListener: InterstitialAdEventListener): Interstitial {
        this.eventListener = eventListener
        return this
    }

    fun loadAd(context: Context) {
        eventListener.onAdLoading()
        when (RemoteConfig.adProvider) {
            "admob" -> {
                loadAdmobInterstitialAd(context)
            }

            "sdkone" -> {
                loadSdkoneInterstitialAd(context)
            }

            else -> {
                eventListener.onAdLoadFailed()
            }
        }
    }

    fun showAd(activity: Activity) {
        when (RemoteConfig.adProvider) {
            "admob" -> {
                showAdmobInterstitialAd(activity)
            }

            "sdkone" -> {
                showSdkoneInterstitialAd(activity)
            }

            else -> {
                eventListener.onAdShowFailed()
            }
        }
    }

    private fun loadAdmobInterstitialAd(context: Context) {
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    eventListener.onAdLoadFailed()
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    super.onAdLoaded(interstitialAd)
                    admobInterstitialAd = interstitialAd
                    eventListener.onAdLoaded()
                }
            })
    }

    private fun showAdmobInterstitialAd(activity: Activity) {
        admobInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
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
        admobInterstitialAd?.show(activity)
    }

    private fun loadSdkoneInterstitialAd(context: Context) {
        ggInterstitialAd = GGInterstitialAd(context, "float-13569")
        ggInterstitialAd?.addListener(object : GGInterstitialEventsListener {
            override fun onAdLoaded() {
                eventListener.onAdLoaded()
            }

            override fun onAdLoadFailed(adErrors: AdErrors) {
                eventListener.onAdLoadFailed()
            }

            override fun onAdShowFailed() {
                eventListener.onAdShowFailed()
            }

            override fun onAdOpened() {
                eventListener.onAdOpened()
            }

            override fun onAdClosed() {
                eventListener.onAdClosed()
            }
        })
        ggInterstitialAd?.loadAd()
    }

    private fun showSdkoneInterstitialAd(activity: Activity) {
        ggInterstitialAd?.show(activity)
    }
}