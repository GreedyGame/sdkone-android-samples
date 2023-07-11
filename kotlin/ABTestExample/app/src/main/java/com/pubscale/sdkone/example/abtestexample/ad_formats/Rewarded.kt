package com.pubscale.sdkone.example.abtestexample.ad_formats

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.core.rewarded_ad.general.GGRewardedAd
import com.pubscale.sdkone.core.rewarded_ad.general.GGRewardedAdsEventListener
import com.pubscale.sdkone.example.abtestexample.event_listener.RewardedAdEventListener
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig

object Rewarded {
    private var ggRewardedAd: GGRewardedAd? = null
    private var admobRewardedAd: RewardedAd? = null

    private var eventListener: RewardedAdEventListener = object : RewardedAdEventListener {
        override fun onReward() {}
        override fun onAdLoading() {}
        override fun onAdLoaded() {}
        override fun onAdLoadFailed() {}
        override fun onAdShowFailed() {}
        override fun onAdOpened() {}
        override fun onAdClosed() {}
    }

    fun setRewardedAdEventListener(eventListener: RewardedAdEventListener): Rewarded {
        this.eventListener = eventListener
        return this
    }

    fun loadAd(context: Context) {
        eventListener.onAdLoading()
        when (RemoteConfig.adProvider) {
            "admob" -> {
                loadAdmobRewardedAd(context)
            }

            "sdkone" -> {
                loadSdkoneRewardedAd(context)
            }

            else -> {
                eventListener.onAdLoadFailed()
            }
        }
    }

    fun showAd(activity: Activity) {
        when (RemoteConfig.adProvider) {
            "admob" -> {
                showAdmobRewardedAd(activity)
            }

            "sdkone" -> {
                showSdkoneRewardedAd(activity)
            }

            else -> {
                eventListener.onAdShowFailed()
            }
        }
    }

    private fun loadAdmobRewardedAd(context: Context) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917",
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    admobRewardedAd = null
                    eventListener.onAdLoadFailed()
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    admobRewardedAd = ad
                    eventListener.onAdLoaded()
                }
            })
    }

    private fun showAdmobRewardedAd(activity: Activity) {
        admobRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
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
        admobRewardedAd?.show(activity) { eventListener.onReward() }
    }


    private fun loadSdkoneRewardedAd(context: Context) {
        ggRewardedAd = GGRewardedAd(context, "float-13571")
        ggRewardedAd?.addListener(object : GGRewardedAdsEventListener {
            override fun onReward() {
                eventListener.onReward()
            }

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
        ggRewardedAd?.loadAd()
    }

    private fun showSdkoneRewardedAd(activity: Activity) {
        ggRewardedAd?.show(activity)
    }
}