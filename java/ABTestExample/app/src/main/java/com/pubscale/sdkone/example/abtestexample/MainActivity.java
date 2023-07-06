package com.pubscale.sdkone.example.abtestexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.pubscale.sdkone.core.adview.general.AdLoadCallback;
import com.pubscale.sdkone.core.adview.general.GGAdview;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.example.abtestexample.databinding.ActivityMainBinding;

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
    }

    private void loadBannerAd() {
//        loadSdkOneBannerAd();
//        loadAdmobBannerAd();
    }

    private void loadAdmobBannerAd() {
        binding.bannerAdStatus.setText("Loading...");

        AdView adView = new AdView(this);
        binding.bannerAd.removeAllViews();
        binding.bannerAd.addView(adView);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

        adView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                binding.bannerAdStatus.setText("Loaded");
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                binding.bannerAdStatus.setText("Load Failed " + loadAdError.getMessage());
            }

            @Override
            public void onAdImpression() {
                super.onAdImpression();
                binding.bannerAdStatus.setText("Impression");
            }

            @Override
            public void onAdClicked() {
                super.onAdClicked();
                binding.bannerAdStatus.setText("Clicked");
            }

            @Override
            public void onAdOpened() {
                super.onAdOpened();
                binding.bannerAdStatus.setText("UI opened");
            }

            @Override
            public void onAdClosed() {
                super.onAdClosed();
                binding.bannerAdStatus.setText("UI closed");
            }
        });

        adView.loadAd(new AdRequest.Builder().build());
    }

    private void loadSdkOneBannerAd() {
        binding.bannerAdStatus.setText("Loading...");

        GGAdview ggAdview = new GGAdview(this);
        binding.bannerAd.removeAllViews();
        binding.bannerAd.addView(ggAdview);
        ggAdview.setUnitId("float-13568");

        ggAdview.loadAd(new AdLoadCallback() {
            @Override
            public void onAdLoaded() {
                binding.bannerAdStatus.setText("Loaded");
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                binding.bannerAdStatus.setText("Load Failed " + adErrors.name());
            }

            @Override
            public void onUiiOpened() {
                binding.bannerAdStatus.setText("UI opened");
            }

            @Override
            public void onUiiClosed() {
                binding.bannerAdStatus.setText("UI closed");
            }

            @Override
            public void onReadyForRefresh() {
                binding.bannerAdStatus.setText("Ready For refresh");
            }
        });
    }
}