package com.pubscale.sdkone.example.abtestexample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.example.abtestexample.ad_formats.Banner;
import com.pubscale.sdkone.example.abtestexample.ad_formats.Native;
import com.pubscale.sdkone.example.abtestexample.databinding.ActivityMainBinding;
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.bannerAdLoadButton.setOnClickListener(view -> {
            loadBannerAd();
        });

        binding.nativeAdLoadButton.setOnClickListener(view -> {
            loadNativeAd();
        });
    }

    private void loadBannerAd() {
        BannerNativeAdEventListener bannerNativeAdEventListener = new BannerNativeAdEventListener() {
            @Override
            public void onAdLoading() {
                binding.bannerAdStatus.setText("Loading...");
            }

            @Override
            public void onAdLoaded() {
                binding.bannerAdStatus.setText("Loaded");
            }

            @Override
            public void onAdLoadFailed() {
                binding.bannerAdStatus.setText("Failed");
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

        Banner.getInstance(bannerNativeAdEventListener).loadAd(this, binding.bannerAd);
    }

    private void loadNativeAd() {
        BannerNativeAdEventListener bannerNativeAdEventListener = new BannerNativeAdEventListener() {
            @Override
            public void onAdLoading() {
                binding.nativeAdStatus.setText("Loading...");
            }

            @Override
            public void onAdLoaded() {
                binding.nativeAdStatus.setText("Loaded");
            }

            @Override
            public void onAdLoadFailed() {
                binding.nativeAdStatus.setText("Failed");
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

        Native.getInstance(bannerNativeAdEventListener).loadAd(this, binding.nativeAd);
    }
}

