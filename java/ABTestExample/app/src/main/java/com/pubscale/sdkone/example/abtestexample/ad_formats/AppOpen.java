package com.pubscale.sdkone.example.abtestexample.ad_formats;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.appopen.AppOpenAd;
import com.pubscale.sdkone.core.app_open_ads.general.AppOpenAdsEventsListener;
import com.pubscale.sdkone.example.abtestexample.event_listener.AppOpenAdEventListener;
import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig;

public class AppOpen {
    private static AppOpen appOpenAd = null;
    private RemoteConfig remoteConfig = RemoteConfig.getInstance();
    private boolean shouldShowOnAppMovedToForeground;
    private boolean isAdmobLoading = false;
    private boolean isAdmobShowing = false;

    private AppOpenAdEventListener eventListener = new AppOpenAdEventListener() {
        @Override
        public void onAdLoaded() {
        }

        @Override
        public void onAdLoadFailed() {
        }

        @Override
        public void onAdShowFailed() {
        }

        @Override
        public void onAdOpened() {
        }

        @Override
        public void onAdClosed() {
        }
    };

    private AppOpen() {
        setShouldShowOnAppMovedToForeground(true);
    }

    public static AppOpen getInstance() {
        if (appOpenAd != null) return appOpenAd;
        appOpenAd = new AppOpen();
        return appOpenAd;
    }

    public AppOpen setShouldShowOnAppMovedToForeground(boolean value) {
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                this.shouldShowOnAppMovedToForeground = value;
                break;
            }
            case "sdkone": {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(value);
                break;
            }
            default: {
            }
        }
        return appOpenAd;
    }

    public AppOpen setAppOpenAdEventListener(AppOpenAdEventListener eventListener) {
        this.eventListener = eventListener;
        return appOpenAd;
    }

    public void loadAd(Activity activity, boolean isStartup) {
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(false);
                loadAdmobAppOpenAd(activity);
                break;
            }
            case "sdkone": {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(true);
                if (isStartup) {
                    loadSdkoneAppOpenAd(activity);
                } else {/* sdkone will handle app open ads */}
                break;
            }
            default: {
            }
        }
    }

    private void loadAdmobAppOpenAd(Activity activity) {
        if (!shouldShowOnAppMovedToForeground) return;
        if(isAdmobLoading) return;
        isAdmobLoading = true;

        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                activity, "ca-app-pub-3940256099942544/3419835294", request,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        isAdmobLoading = false;
                        eventListener.onAdLoaded();
                        showAdmobAppOpenAd(ad, activity);
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        isAdmobLoading = false;
                        eventListener.onAdLoadFailed();
                    }
                });

    }

    private void showAdmobAppOpenAd(AppOpenAd admobAppOpenAd, Activity activity) {
        if(isAdmobShowing) return;
        admobAppOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                super.onAdClicked();
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                super.onAdDismissedFullScreenContent();
                isAdmobShowing = false;
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
                isAdmobShowing = true;
                eventListener.onAdOpened();
            }
        });

        admobAppOpenAd.show(activity);
    }

    private void loadSdkoneAppOpenAd(Activity activity) {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(true);

        GGAppOpenAds.addListener(new AppOpenAdsEventsListener() {
            @Override
            public void onAdLoaded() {
                eventListener.onAdLoaded();
                GGAppOpenAds.show(activity);
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                eventListener.onAdLoadFailed();
                GGAppOpenAds.removeListener(this);
            }

            @Override
            public void onAdShowFailed() {
                eventListener.onAdShowFailed();
                GGAppOpenAds.removeListener(this);
            }

            @Override
            public void onAdOpened() {
                eventListener.onAdOpened();
            }

            @Override
            public void onAdClosed() {
                eventListener.onAdClosed();
                GGAppOpenAds.removeListener(this);
            }
        });

        GGAppOpenAds.loadAd("float-13570");
    }

}
