package com.pubscale.sdkone.example.abtestexample;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
        AppOpen appOpenAd = AppOpen.getInstance();
        AppOpenAdEventListener appOpenAdEventListener = new AppOpenAdEventListener() {
            @Override
            public void onAdLoading() {
                binding.appOpenStatus.setText("Loading...");
            }

            @Override
            public void onAdLoaded() {
                binding.appOpenStatus.setText("Loaded");
                appOpenAd.showAd(SplashActivity.this);
            }

            @Override
            public void onAdLoadFailed() {
                binding.appOpenStatus.setText("Failed");
                openMainActivity();
            }

            @Override
            public void onAdShowFailed() {
                binding.appOpenStatus.setText("Show failed");
                openMainActivity();
            }

            @Override
            public void onAdOpened() {
                binding.appOpenStatus.setText("Opened");
            }

            @Override
            public void onAdClosed() {
                binding.appOpenStatus.setText("Closed");
                openMainActivity();
            }

            @Override
            public void onAdDisabled() {
                binding.appOpenStatus.setText("Disabled");
                Toast.makeText(SplashActivity.this, "App open ad disabled", Toast.LENGTH_SHORT).show();
                openMainActivity();
            }
        };

        appOpenAd.setAppOpenAdEventListener(appOpenAdEventListener);
        appOpenAd.loadAd(this);
    }

    private void openMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class)
                .setFlags((Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK)));
    }
}