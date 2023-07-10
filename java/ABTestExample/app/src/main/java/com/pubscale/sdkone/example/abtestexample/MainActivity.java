package com.pubscale.sdkone.example.abtestexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.core.models.core.App;
import com.pubscale.sdkone.example.abtestexample.ad_formats.AppOpen;
import com.pubscale.sdkone.example.abtestexample.ad_formats.Banner;
import com.pubscale.sdkone.example.abtestexample.ad_formats.Interstitial;
import com.pubscale.sdkone.example.abtestexample.ad_formats.Native;
import com.pubscale.sdkone.example.abtestexample.ad_formats.Rewarded;
import com.pubscale.sdkone.example.abtestexample.databinding.ActivityMainBinding;
import com.pubscale.sdkone.example.abtestexample.event_listener.AppOpenAdEventListener;
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener;
import com.pubscale.sdkone.example.abtestexample.event_listener.InterstitialAdEventListener;
import com.pubscale.sdkone.example.abtestexample.event_listener.RewardedAdEventListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Interstitial interstitialAd = null;
    private Rewarded rewardedAd = null;
    private AppOpen appOpenAd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        appOpenAd = AppOpen.getInstance();
        binding.appOpenLoadButton.setOnClickListener(view -> {
            loadAppOpenAd();
        });

        binding.appOpenShowButton.setOnClickListener(view -> {
            showAppOpenAd();
        });

        binding.bannerAdLoadButton.setOnClickListener(view -> {
            loadBannerAd();
        });

        binding.nativeAdLoadButton.setOnClickListener(view -> {
            loadNativeAd();
        });

        binding.interstitialLoadButton.setOnClickListener(view -> {
            loadInterstitialAd(false);
        });

        binding.interstitialShowButton.setOnClickListener(view -> {
            showInterstitialAd();
        });

        binding.goToNextActivityButton.setOnClickListener(view -> {
            loadInterstitialAd(true);
        });

        binding.rewardedLoadButton.setOnClickListener(view -> {
            loadRewardedAd();
        });

        binding.rewardedShowButton.setOnClickListener(view -> {
            showRewardedAd();
        });

    }

    private void loadAppOpenAd() {
        AppOpenAdEventListener appOpenAdEventListener = new AppOpenAdEventListener() {
            @Override
            public void onAdLoading() {
                binding.appOpenAdStatus.setText("Loading...");
                binding.appOpenLoadButton.setEnabled(false);
            }

            @Override
            public void onAdLoaded() {
                binding.appOpenAdStatus.setText("Loaded");
                binding.appOpenShowButton.setEnabled(true);
            }

            @Override
            public void onAdLoadFailed() {
                binding.appOpenAdStatus.setText("Failed");
                binding.appOpenLoadButton.setEnabled(true);
            }

            @Override
            public void onAdShowFailed() {
                binding.appOpenAdStatus.setText("Show failed");
                binding.appOpenShowButton.setEnabled(false);
                binding.appOpenLoadButton.setEnabled(true);
            }

            @Override
            public void onAdOpened() {
                binding.appOpenAdStatus.setText("Opened");
                binding.appOpenShowButton.setEnabled(false);
                binding.appOpenLoadButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                binding.appOpenAdStatus.setText("Closed");
            }
        };

        appOpenAd.setAppOpenAdEventListener(appOpenAdEventListener).loadAd(this, true);
    }

    private void showAppOpenAd() {
        appOpenAd.showAd(this, true);
    }

    private void loadBannerAd() {
        BannerNativeAdEventListener bannerNativeAdEventListener = new BannerNativeAdEventListener() {
            @Override
            public void onAdLoading() {
                binding.bannerAdStatus.setText("Loading...");
                binding.bannerAdLoadButton.setEnabled(false);
            }

            @Override
            public void onAdLoaded() {
                binding.bannerAdStatus.setText("Loaded");
                binding.bannerAdLoadButton.setEnabled(true);
            }

            @Override
            public void onAdLoadFailed() {
                binding.bannerAdStatus.setText("Failed");
                binding.bannerAdLoadButton.setEnabled(true);
            }

            @Override
            public void onUiiOpened() {
                binding.bannerAdStatus.setText("Opened");
            }

            @Override
            public void onUiiClosed() {
                binding.bannerAdStatus.setText("Closed");
            }

            @Override
            public void onReadyForRefresh() {
                binding.bannerAdStatus.setText("Ready for refresh");
            }
        };

        Banner.getInstance()
                .setBannerNativeAdEventListener(bannerNativeAdEventListener)
                .loadAd(this, binding.bannerAd);
    }

    private void loadNativeAd() {
        BannerNativeAdEventListener bannerNativeAdEventListener = new BannerNativeAdEventListener() {
            @Override
            public void onAdLoading() {
                binding.nativeAdStatus.setText("Loading...");
                binding.nativeAdLoadButton.setEnabled(false);
            }

            @Override
            public void onAdLoaded() {
                binding.nativeAdStatus.setText("Loaded");
                binding.nativeAdLoadButton.setEnabled(true);
            }

            @Override
            public void onAdLoadFailed() {
                binding.nativeAdStatus.setText("Failed");
                binding.nativeAdLoadButton.setEnabled(true);
            }

            @Override
            public void onUiiOpened() {
                binding.nativeAdStatus.setText("Opened");
            }

            @Override
            public void onUiiClosed() {
                binding.nativeAdStatus.setText("Closed");
            }

            @Override
            public void onReadyForRefresh() {
                binding.nativeAdStatus.setText("Ready for refresh");
            }
        };

        Native.getInstance()
                .setBannerNativeAdEventListener(bannerNativeAdEventListener)
                .loadAd(this, binding.nativeAd);
    }

    private void loadInterstitialAd(boolean goToNextActivity) {
        InterstitialAdEventListener interstitialAdEventListener = new InterstitialAdEventListener() {
            @Override
            public void onAdLoading() {
                binding.interstitialAdStatus.setText("Loading...");
                binding.interstitialLoadButton.setEnabled(false);
            }

            @Override
            public void onAdLoaded() {
                binding.interstitialAdStatus.setText("Loaded");
                if(goToNextActivity) showInterstitialAd();
                else binding.interstitialShowButton.setEnabled(true);
            }

            @Override
            public void onAdLoadFailed() {
                binding.interstitialAdStatus.setText("Load failed");
                binding.interstitialLoadButton.setEnabled(true);
                if(goToNextActivity) proceedToNextActivity();
            }

            @Override
            public void onAdShowFailed() {
                binding.interstitialAdStatus.setText("Show failed");
                binding.interstitialShowButton.setEnabled(false);
                binding.interstitialLoadButton.setEnabled(true);
                if(goToNextActivity) proceedToNextActivity();
            }

            @Override
            public void onAdOpened() {
                binding.interstitialAdStatus.setText("Opened");
                binding.interstitialShowButton.setEnabled(false);
                binding.interstitialLoadButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                binding.interstitialAdStatus.setText("Closed");
                if(goToNextActivity) proceedToNextActivity();
            }
        };

        interstitialAd = Interstitial.getInstance()
                .setInterstitialAdEventListener(interstitialAdEventListener);
        interstitialAd.loadAd(this);
    }

    private void showInterstitialAd() {
        interstitialAd.showAd(this);
    }

    private void proceedToNextActivity() {
        startActivity(new Intent(this, DummyActivity.class));
    }

    private void loadRewardedAd() {
        RewardedAdEventListener rewardedAdEventListener = new RewardedAdEventListener() {
            @Override
            public void onReward() {
                binding.rewardedAdStatus.setText("Rewarded");
                Toast.makeText(getApplicationContext(), "Rewarded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdLoading() {
                binding.rewardedAdStatus.setText("Loading...");
                binding.rewardedLoadButton.setEnabled(false);
            }

            @Override
            public void onAdLoaded() {
                binding.rewardedAdStatus.setText("Loaded");
                binding.rewardedShowButton.setEnabled(true);
            }

            @Override
            public void onAdLoadFailed() {
                binding.rewardedAdStatus.setText("Failed");
                binding.rewardedLoadButton.setEnabled(true);
            }

            @Override
            public void onAdShowFailed() {
                binding.rewardedAdStatus.setText("Show failed");
                binding.rewardedShowButton.setEnabled(false);
                binding.rewardedLoadButton.setEnabled(true);
            }

            @Override
            public void onAdOpened() {
                binding.rewardedAdStatus.setText("Opened");
                binding.rewardedShowButton.setEnabled(false);
                binding.rewardedLoadButton.setEnabled(true);
            }

            @Override
            public void onAdClosed() {
                binding.rewardedAdStatus.setText("Closed");
            }
        };

        rewardedAd = Rewarded.getInstance()
                .setRewardedAdEventListener(rewardedAdEventListener);
        rewardedAd.loadAd(this);
    }

    private void showRewardedAd() {
        rewardedAd.showAd(this);
    }
}

