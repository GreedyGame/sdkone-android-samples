package com.pubscale.sdkone.example.abtestexample.ad_formats

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.ads.nativetemplates.NativeTemplateStyle
import com.google.android.ads.nativetemplates.TemplateView
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.pubscale.sdkone.core.adview.general.AdLoadCallback
import com.pubscale.sdkone.core.adview.general.GGAdview
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.example.abtestexample.databinding.MediumAdmobTemplateBinding
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig

object Native {
    private var eventListener: BannerNativeAdEventListener = object : BannerNativeAdEventListener {
        override fun onAdLoading() {}
        override fun onAdLoaded() {}
        override fun onAdLoadFailed() {}
        override fun onUiiOpened() {}
        override fun onUiiClosed() {}
        override fun onReadyForRefresh() {}
    }

    fun setBannerNativeAdEventListener(eventListener: BannerNativeAdEventListener): Native {
        this.eventListener = eventListener
        return this
    }

    fun loadAd(context: Context, adContainer: ViewGroup) {
        eventListener.onAdLoading()
        when (RemoteConfig.adProvider) {
            "admob" -> {
                loadAdmobNativeAd(context, adContainer)
            }

            "sdkone" -> {
                loadSdkoneNativeAd(context, adContainer)
            }

            else -> {
                eventListener.onAdLoadFailed()
            }
        }
    }

    private fun loadAdmobNativeAd(context: Context, adContainer: ViewGroup) {
        val adLoader = AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { nativeAd: NativeAd? ->
                val adView: MediumAdmobTemplateBinding =
                    MediumAdmobTemplateBinding.inflate(
                        LayoutInflater.from(context),
                        adContainer,
                        false
                    )
                val template: TemplateView = adView.myTemplate
                template.setStyles(NativeTemplateStyle.Builder().build())
                template.setNativeAd(nativeAd)
                adContainer.removeAllViews()
                adContainer.addView(template)
            }.withAdListener(object : AdListener() {
                override fun onAdClicked() {
                    super.onAdClicked()
                }

                override fun onAdClosed() {
                    super.onAdClosed()
                    eventListener.onUiiClosed()
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    super.onAdFailedToLoad(loadAdError)
                    eventListener.onAdLoadFailed()
                }

                override fun onAdImpression() {
                    super.onAdImpression()
                }

                override fun onAdLoaded() {
                    super.onAdLoaded()
                    eventListener.onAdLoaded()
                }

                override fun onAdOpened() {
                    super.onAdOpened()
                    eventListener.onUiiOpened()
                }
            }).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    private fun loadSdkoneNativeAd(context: Context, adContainer: ViewGroup) {
        val ggAdview = GGAdview(context)
        adContainer.removeAllViews()
        adContainer.addView(ggAdview)
        ggAdview.unitId = "float-13582"
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