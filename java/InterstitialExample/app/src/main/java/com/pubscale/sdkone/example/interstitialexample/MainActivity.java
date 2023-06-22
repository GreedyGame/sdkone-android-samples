package com.pubscale.sdkone.example.interstitialexample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds;
import com.pubscale.sdkone.core.interstitial.general.GGInterstitialAd;
import com.pubscale.sdkone.core.interstitial.general.GGInterstitialEventsListener;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.example.interstitialexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Disabling auto app open ads
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false);

        binding.btnLoad.setOnClickListener(v -> {
            loadInterstitialAd();
        });
        binding.btnShow.setOnClickListener(v -> {
            showAd();
        });
    }

    private GGInterstitialAd mAd = null;

    private void loadInterstitialAd() {
        mAd = new GGInterstitialAd(this, "float-13569");

        binding.tvStatus.setText("Ad Loading");

        mAd.addListener(new GGInterstitialEventsListener() {
            @Override
            public void onAdLoaded() {
                binding.tvStatus.setText("Ad Loaded");
                binding.btnShow.setEnabled(true);
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                mAd.removeListener(this);
                binding.tvStatus.setText("Ad Load Failed - " + adErrors.name());
            }

            @Override
            public void onAdShowFailed() {
                mAd.removeListener(this);
                binding.tvStatus.setText("Ad Show Failed");
            }

            @Override
            public void onAdOpened() {
                Toast.makeText(MainActivity.this, "Ad Opened", Toast.LENGTH_SHORT).show();
                binding.tvStatus.setText("Ad Opened");
            }

            @Override
            public void onAdClosed() {
                binding.tvStatus.setText("Ad Closed");
                binding.btnShow.setEnabled(false);

                //Remove listener to prevent memory leaks
                mAd.removeListener(this);
            }
        });

        //Load ad after adding a listener
        mAd.loadAd();
    }

    private void showAd() {
        //Show the ad
        mAd.show(this);
    }
}