package com.pubscale.sdkone.example.abtestexample.ad_formats;

import android.app.Activity;
import android.content.Context;

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
import com.pubscale.sdkone.example.abtestexample.utils.SharedPref;

public class AppOpen {
    private static AppOpen appOpenAd = null;
    private RemoteConfig remoteConfig = RemoteConfig.getInstance();
    private boolean isAppOpenAdDisabled;
    private SharedPref sharedPref;
    private AppOpenAd admobAppOpenAd = null;

    private AppOpenAdEventListener eventListener = new AppOpenAdEventListener() {
        @Override
        public void onAdLoading() {
        }

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

        @Override
        public void onAdDisabled() {
        }
    };

    private AppOpen() {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false);
        sharedPref = SharedPref.getInstance();
        isAppOpenAdDisabled = sharedPref.isAppOpenAdDisabled();
    }

    public static AppOpen getInstance() {
        if (appOpenAd != null) return appOpenAd;
        appOpenAd = new AppOpen();
        return appOpenAd;
    }

    public AppOpen setAppOpenAdEventListener(AppOpenAdEventListener eventListener) {
        this.eventListener = eventListener;
        return appOpenAd;
    }

    public void disableAppOpenAd(boolean value) {
        this.isAppOpenAdDisabled = value;
        sharedPref.disableAppOpenAd(value);
    }

    public void loadAd(Context context) {
        if(isAppOpenAdDisabled) {
            eventListener.onAdDisabled();
            return;
        }
        eventListener.onAdLoading();
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                loadAdmobAppOpenAd(context);
                break;
            }
            case "sdkone": {
                loadSdkoneAppOpenAd();
                break;
            }
            default: {
                eventListener.onAdLoadFailed();
            }
        }
    }

    public void showAd(Activity activity) {
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                showAdmobAppOpenAd(activity);
                break;
            }
            case "sdkone": {
                GGAppOpenAds.show(activity);
                break;
            }
            default: {
            }
        }
    }

    private void loadAdmobAppOpenAd(Context context) {
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                context, "ca-app-pub-3940256099942544/3419835294", request,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        eventListener.onAdLoaded();
                        admobAppOpenAd = ad;
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        eventListener.onAdLoadFailed();
                    }
                });

    }

    private void showAdmobAppOpenAd(Activity activity) {
        if(admobAppOpenAd == null) {
            eventListener.onAdShowFailed();
            return;
        }
        admobAppOpenAd.setFullScreenContentCallback(new FullScreenContentCallback() {
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

        admobAppOpenAd.show(activity);
    }

    private void loadSdkoneAppOpenAd() {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(true);

        GGAppOpenAds.addListener(new AppOpenAdsEventsListener() {
            @Override
            public void onAdLoaded() {
                eventListener.onAdLoaded();
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
