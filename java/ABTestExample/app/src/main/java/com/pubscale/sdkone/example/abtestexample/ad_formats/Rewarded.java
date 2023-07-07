package com.pubscale.sdkone.example.abtestexample.ad_formats;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.core.rewarded_ad.general.GGRewardedAd;
import com.pubscale.sdkone.core.rewarded_ad.general.GGRewardedAdsEventListener;
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener;
import com.pubscale.sdkone.example.abtestexample.event_listener.RewardedAdEventListener;
import com.pubscale.sdkone.example.abtestexample.utils.RemoteConfig;

public class Rewarded {
    private static Rewarded rewardedAd = null;
    private RemoteConfig remoteConfig = RemoteConfig.getInstance();
    private RewardedAdEventListener eventListener;
    private GGRewardedAd ggRewardedAd = null;
    private RewardedAd admobRewardedAd = null;

    private Rewarded(RewardedAdEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public static synchronized Rewarded getInstance(RewardedAdEventListener eventListener) {
        if (rewardedAd != null) return rewardedAd;
        rewardedAd = new Rewarded(eventListener);
        return rewardedAd;
    }

    public void loadAd(Context context) {
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                loadAdmobRewardedAd(context);
                break;
            }
            case "sdkone": {
                loadSdkoneRewardedAd(context);
                break;
            }
            default: {

            }
        }
    }

    public void showAd(Activity activity) {
        switch (remoteConfig.getAdProvider()) {
            case "admob": {
                showAdmobRewardedAd(activity);
                break;
            }
            case "sdkone": {
                showSdkoneRewardedAd(activity);
                break;
            }
            default: {

            }
        }
    }

    private void loadAdmobRewardedAd(Context context) {
        eventListener.onAdLoading();
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(context, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        admobRewardedAd = null;
                        eventListener.onAdLoadFailed();
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        admobRewardedAd = ad;
                        eventListener.onAdLoaded();
                    }
                });
    }

    private void showAdmobRewardedAd(Activity activity) {
        if(admobRewardedAd == null) return;
        admobRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
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

        admobRewardedAd.show(activity, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                eventListener.onReward();
            }
        });
    }


    private void loadSdkoneRewardedAd(Context context) {
        eventListener.onAdLoading();
        ggRewardedAd = new GGRewardedAd(context, "float-13571");
        ggRewardedAd.addListener(new GGRewardedAdsEventListener() {
            @Override
            public void onReward() {
                eventListener.onReward();
            }

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

        ggRewardedAd.loadAd();
    }

    private void showSdkoneRewardedAd(Activity activity) {
        if (ggRewardedAd == null) return;
        ggRewardedAd.show(activity);
    }
}
