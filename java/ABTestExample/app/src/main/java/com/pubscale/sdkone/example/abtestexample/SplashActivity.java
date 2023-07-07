package com.pubscale.sdkone.example.abtestexample;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.example.abtestexample.ad_formats.AppOpen;
import com.pubscale.sdkone.example.abtestexample.databinding.ActivitySplashBinding;
import com.pubscale.sdkone.example.abtestexample.event_listener.AppOpenAdEventListener;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        loadAppOpenAd();
    }

    private void loadAppOpenAd() {
        AppOpenAdEventListener appOpenAdEventListener = new AppOpenAdEventListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdLoadFailed() {
                openMainActivity();
            }

            @Override
            public void onAdShowFailed() {
                openMainActivity();
            }

            @Override
            public void onAdOpened() {
            }

            @Override
            public void onAdClosed() {
                openMainActivity();
            }
        };

        AppOpen.getInstance().setAppOpenAdEventListener(appOpenAdEventListener).loadAd(this, true);
    }

    private void openMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class)
                .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
    }
}