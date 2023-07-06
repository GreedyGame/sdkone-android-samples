package com.pubscale.sdkone.example.abtestexample.ad_formats;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.pubscale.sdkone.core.adview.general.AdLoadCallback;
import com.pubscale.sdkone.core.adview.general.GGAdview;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener;
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig;

public class Banner {
    private static Banner bannerAd = null;
    private RemoteConfig remoteConfig = RemoteConfig.getInstance();
    private BannerNativeAdEventListener eventListener;

    private Banner(BannerNativeAdEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public static synchronized Banner getInstance(BannerNativeAdEventListener eventListener) {
        if (bannerAd != null) return bannerAd;
        bannerAd = new Banner(eventListener);
        return bannerAd;
    }

    public void loadAd(Context context, ViewGroup adContainer) {
        switch (remoteConfig.adProvider()) {
            case "admob": {
                loadAdmobBannerAd(context, adContainer);
                break;
            }
            case "sdkone": {
                loadSdkoneBannerAd(context, adContainer);
                break;
            }
            default: {

            }
        }
    }

    private void loadAdmobBannerAd(Context context, ViewGroup adContainer) {
        eventListener.onAdLoading();
        AdView adView = new AdView(context);
        adContainer.removeAllViews();
        adContainer.addView(adView);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                eventListener.onAdLoaded();
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
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                eventListener.onUiiOpened();
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                eventListener.onUiiClosed();
            }
        });

        adView.loadAd(new AdRequest.Builder().build());
    }

    private void loadSdkoneBannerAd(Context context, ViewGroup adContainer) {
        eventListener.onAdLoading();
        GGAdview ggAdview = new GGAdview(context);
        adContainer.removeAllViews();
        adContainer.addView(ggAdview);
        ggAdview.setUnitId("float-13568");

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
