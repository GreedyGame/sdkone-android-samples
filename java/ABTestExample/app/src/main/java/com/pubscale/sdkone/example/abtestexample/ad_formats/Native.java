package com.pubscale.sdkone.example.abtestexample.ad_formats;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.pubscale.sdkone.core.adview.general.AdLoadCallback;
import com.pubscale.sdkone.core.adview.general.GGAdview;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.example.abtestexample.databinding.MediumAdmobTemplateBinding;
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener;
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig;

public class Native {
    private static Native nativeAd = null;
    private RemoteConfig remoteConfig = RemoteConfig.getInstance();
    private BannerNativeAdEventListener eventListener;
    private Native(BannerNativeAdEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public static synchronized Native getInstance(BannerNativeAdEventListener eventListener) {
        if(nativeAd != null) return nativeAd;
        nativeAd = new Native(eventListener);
        return nativeAd;
    }

    public void loadAd(Context context, ViewGroup adContainer) {
        switch (remoteConfig.adProvider()) {
            case "admob" : {
                loadAdmobNativeAd(context, adContainer);
                break;
            }
            case "sdkone": {
                loadSdkoneNativeAd(context, adContainer);
                break;
            }
            default:{
            }
        }
    }
    private void loadAdmobNativeAd(Context context, ViewGroup adContainer) {
        eventListener.onAdLoading();
        AdLoader adLoader = new AdLoader.Builder(context, "ca-app-pub-3940256099942544/2247696110")
                .forNativeAd(nativeAd -> {
                    MediumAdmobTemplateBinding adView = MediumAdmobTemplateBinding.inflate(LayoutInflater.from(context), adContainer, false);
                    TemplateView template = adView.myTemplate;
                    template.setStyles(new NativeTemplateStyle.Builder().build());
                    template.setNativeAd(nativeAd);
                    adContainer.removeAllViews();
                    adContainer.addView(template);
                }).withAdListener(new AdListener() {
                    @Override
                    public void onAdClicked() {
                        super.onAdClicked();
                    }

                    @Override
                    public void onAdClosed() {
                        super.onAdClosed();
                        eventListener.onUiiClosed();
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        super.onAdFailedToLoad(loadAdError);
                        eventListener.onAdLoadFailed();
                    }

                    @Override
                    public void onAdImpression() {
                        super.onAdImpression();
                    }

                    @Override
                    public void onAdLoaded() {
                        super.onAdLoaded();
                        eventListener.onAdLoaded();
                    }

                    @Override
                    public void onAdOpened() {
                        super.onAdOpened();
                        eventListener.onUiiOpened();
                    }
                }).build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }
    private void loadSdkoneNativeAd(Context context, ViewGroup adContainer) {
        eventListener.onAdLoading();
        GGAdview ggAdview = new GGAdview(context);
        adContainer.removeAllViews();
        adContainer.addView(ggAdview);
        ggAdview.setUnitId("float-13582");

        ggAdview.loadAd(new AdLoadCallback() {
            @Override
            public void onAdLoaded() {
                eventListener.onAdLoaded();
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                eventListener.onAdLoadFailed();
            }

            @Override
            public void onUiiOpened() {
                eventListener.onUiiOpened();
            }

            @Override
            public void onUiiClosed() {
                eventListener.onUiiClosed();
            }

            @Override
            public void onReadyForRefresh() {
                eventListener.onReadyForRefresh();
            }
        });
    }
}
