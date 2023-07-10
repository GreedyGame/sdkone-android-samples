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

public class AppOpen {
    private static AppOpen appOpenAd = null;
    private RemoteConfig remoteConfig = RemoteConfig.getInstance();
    private AppOpenAd admobAppOpenAd = null;
    private boolean isLoadingAd = false;
    public boolean isLoadingAd() {
        return isLoadingAd;
    }
    private boolean isShowingAd = false;
    public boolean isShowingAd() {
        return isShowingAd;
    }
    public boolean isAdAvailable() {
        return admobAppOpenAd != null;
    }

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
    };

    private AppOpen() {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false);
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

    public void loadAd(Context context, boolean manual) {
        eventListener.onAdLoading();
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(false);
                loadAdmobAppOpenAd(context);
                break;
            }
            case "sdkone": {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(true);
                if(manual) { loadSdkoneAppOpenAd(); }
                else { /** SDKOne will handle auto app open ads upon app coming to foreground */ }
                break;
            }
            default: {
                GGAppOpenAds.setShouldShowOnAppMovedToForeground(false);
                eventListener.onAdLoadFailed();
            }
        }
    }

    public void showAd(Activity activity, boolean manual) {
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                showAdmobAppOpenAd(activity);
                break;
            }
            case "sdkone": {
                if(manual) { GGAppOpenAds.show(activity); }
                else { /** SDKOne will handle auto app open ads upon app coming to foreground */ }
                break;
            }
            default: {
                eventListener.onAdShowFailed();
            }
        }
    }

    private void loadAdmobAppOpenAd(Context context) {
        isLoadingAd = true;
        AdRequest request = new AdRequest.Builder().build();
        AppOpenAd.load(
                context, "ca-app-pub-3940256099942544/3419835294", request,
                new AppOpenAd.AppOpenAdLoadCallback() {
                    @Override
                    public void onAdLoaded(AppOpenAd ad) {
                        admobAppOpenAd = ad;
                        isLoadingAd = false;
                        eventListener.onAdLoaded();
                    }

                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        isLoadingAd = false;
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
                isShowingAd = false;
                eventListener.onAdClosed();
            }

            @Override
            public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                super.onAdFailedToShowFullScreenContent(adError);
                isShowingAd = false;
                eventListener.onAdShowFailed();
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
            }

            @Override
            public void onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent();
                isShowingAd = true;
                eventListener.onAdOpened();
            }
        });

        admobAppOpenAd.show(activity);
    }

    private void loadSdkoneAppOpenAd() {
        isLoadingAd = true;
        GGAppOpenAds.addListener(new AppOpenAdsEventsListener() {
            @Override
            public void onAdLoaded() {
                isLoadingAd = false;
                eventListener.onAdLoaded();
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                isLoadingAd = false;
                eventListener.onAdLoadFailed();
                GGAppOpenAds.removeListener(this);
            }

            @Override
            public void onAdShowFailed() {
                isShowingAd = false;
                eventListener.onAdShowFailed();
                GGAppOpenAds.removeListener(this);
            }

            @Override
            public void onAdOpened() {
                isShowingAd = true;
                eventListener.onAdOpened();
            }

            @Override
            public void onAdClosed() {
                isShowingAd = false;
                eventListener.onAdClosed();
                GGAppOpenAds.removeListener(this);
            }
        });

        GGAppOpenAds.loadAd("float-13570");
    }

}
