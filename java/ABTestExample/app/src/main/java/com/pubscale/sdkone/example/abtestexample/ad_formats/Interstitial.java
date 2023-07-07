package com.pubscale.sdkone.example.abtestexample.ad_formats;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.pubscale.sdkone.core.interstitial.general.GGInterstitialAd;
import com.pubscale.sdkone.core.interstitial.general.GGInterstitialEventsListener;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.example.abtestexample.event_listener.InterstitialEventListener;
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig;

public class Interstitial {
    private static Interstitial interstitialAd = null;
    private RemoteConfig remoteConfig = RemoteConfig.getInstance();
    private InterstitialEventListener eventListener;
    private InterstitialAd admobInterstitialAd = null;
    private GGInterstitialAd ggInterstitialAd = null;

    private Interstitial(InterstitialEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public static synchronized Interstitial getInstance(InterstitialEventListener eventListener) {
        if (interstitialAd != null) return interstitialAd;
        interstitialAd = new Interstitial(eventListener);
        return interstitialAd;
    }

    public void loadAd(Context context) {
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                loadAdmobInterstitialAd(context);
                break;
            }
            case "sdkone": {
                loadSdkoneInterstitialAd(context);
                break;
            }
            default: {

            }
        }
    }

    public void showAd(Activity activity) {
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                showAdmobInterstitialAd(activity);
                break;
            }
            case "sdkone": {
                showSdkoneInterstitialAd(activity);
                break;
            }
            default: {

            }
        }
    }

    private void loadAdmobInterstitialAd(Context context) {
        eventListener.onAdLoading();
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(context, "ca-app-pub-3940256099942544/1033173712", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                eventListener.onAdLoadFailed();
            }

            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                admobInterstitialAd = interstitialAd;
                eventListener.onAdLoaded();
            }
        });

    }

    private void showAdmobInterstitialAd(Activity activity) {
        if (admobInterstitialAd == null) return;

        admobInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                eventListener.onAdClosed();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                eventListener.onAdShowFailed();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                eventListener.onAdOpened();
            }
        });

        admobInterstitialAd.show(activity);
    }

    private void loadSdkoneInterstitialAd(Context context) {
        eventListener.onAdLoading();
        ggInterstitialAd = new GGInterstitialAd(context, "float-13569");

        ggInterstitialAd.addListener(new GGInterstitialEventsListener() {
            @Override
            public void onAdLoaded() {
                eventListener.onAdLoaded();
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                eventListener.onAdLoadFailed();
            }

            @Override
            public void onAdShowFailed() {
                eventListener.onAdShowFailed();
            }

            @Override
            public void onAdOpened() {
                eventListener.onAdOpened();
            }

            @Override
            public void onAdClosed() {
                eventListener.onAdClosed();
            }
        });

        ggInterstitialAd.loadAd();
    }

    private void showSdkoneInterstitialAd(Activity activity) {
        if (ggInterstitialAd == null) return;
        ggInterstitialAd.show(activity);
    }

}
