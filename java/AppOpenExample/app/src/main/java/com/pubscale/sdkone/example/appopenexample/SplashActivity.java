package com.pubscale.sdkone.example.appopenexample;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.core.app_open_ads.general.AppOpenAdsEventsListener;
import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.example.appopenexample.databinding.ActivitySplashBinding;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());

        loadAppOpenAds();
    }

    private void loadAppOpenAds() {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(true);

        GGAppOpenAds.addListener(new AppOpenAdsEventsListener() {
            @Override
            public void onAdLoaded() {
                GGAppOpenAds.show(SplashActivity.this);
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                GGAppOpenAds.removeListener(this);
                openMainActivity();
            }

            @Override
            public void onAdShowFailed() {
                GGAppOpenAds.removeListener(this);
                openMainActivity();
            }

            @Override
            public void onAdOpened() {

            }

            @Override
            public void onAdClosed() {
                GGAppOpenAds.removeListener(this);
                openMainActivity();
            }
        });

        GGAppOpenAds.loadAd("float-13570");
    }

    private void openMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class)
                .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
    }
}