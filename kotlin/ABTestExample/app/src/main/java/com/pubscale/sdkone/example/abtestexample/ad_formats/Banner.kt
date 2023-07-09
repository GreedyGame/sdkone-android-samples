package com.pubscale.sdkone.example.abtestexample.ad_formats

import android.content.Context
import android.view.ViewGroup
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.pubscale.sdkone.core.adview.general.AdLoadCallback
import com.pubscale.sdkone.core.adview.general.GGAdview
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig

object Banner {
    private val remoteConfig: RemoteConfig = RemoteConfig
    private var eventListener: BannerNativeAdEventListener = object : BannerNativeAdEventListener {
        override fun onAdLoading() {}
        override fun onAdLoaded() {}
        override fun onAdLoadFailed() {}
        override fun onUiiOpened() {}
        override fun onUiiClosed() {}
        override fun onReadyForRefresh() {}
    }

    fun setBannerNativeAdEventListener(eventListener: BannerNativeAdEventListener): Banner {
        this.eventListener = eventListener
        return this
    }

    fun loadAd(context: Context, adContainer: ViewGroup) {
        eventListener.onAdLoading()
        when (remoteConfig.adProvider) {
            "admob" -> {
                loadAdmobBannerAd(context, adContainer)
            }

            "sdkone" -> {
                loadSdkoneBannerAd(context, adContainer)
            }

            else -> {
                eventListener.onAdLoadFailed()
            }
        }
    }

    private fun loadAdmobBannerAd(context: Context, adContainer: ViewGroup) {
        val adView = AdView(context)
        adContainer.removeAllViews()
        adContainer.addView(adView)
        adView.setAdSize(AdSize.BANNER)
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        adView.adListener = object : AdListener() {
            override fun onAdLoaded() {
                super.onAdLoaded()
                eventListener.onAdLoaded()
            }

            override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                super.onAdFailedToLoad(loadAdError)
                eventListener.onAdLoadFailed()
            }

            override fun onAdImpression() {
                super.onAdImpression()
            }

            override fun onAdClicked() {
                super.onAdClicked()
            }

            override fun onAdOpened() {
                super.onAdOpened()
                eventListener.onUiiOpened()
            }

            override fun onAdClosed() {
                super.onAdClosed()
                eventListener.onUiiClosed()
            }
        }
        adView.loadAd(AdRequest.Builder().build())
    }

    private fun loadSdkoneBannerAd(context: Context, adContainer: ViewGroup) {
        val ggAdview = GGAdview(context)
        adContainer.removeAllViews()
        adContainer.addView(ggAdview)
        ggAdview.unitId = "float-13568"
        ggAdview.loadAd(object : AdLoadCallback {
            override fun onAdLoaded() {
                eventListener.onAdLoaded()
            }

            override fun onAdLoadFailed(adErrors: AdErrors) {
                eventListener.onAdLoadFailed()
            }

            override fun onUiiOpened() {
                eventListener.onUiiOpened()
            }

            override fun onUiiClosed() {
                eventListener.onUiiClosed()
            }

            override fun onReadyForRefresh() {
                eventListener.onReadyForRefresh()
            }
        })
    }
}