package com.pubscale.sdkone.example.rewardedexample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.core.rewarded_ad.general.GGRewardedAd;
import com.pubscale.sdkone.core.rewarded_ad.general.GGRewardedAdsEventListener;
import com.pubscale.sdkone.example.rewardedexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Disabling auto app open ads
        GGAppOpenAds.INSTANCE.setShouldShowOnAppMovedToForeground(false);

        binding.btnLoad.setOnClickListener(v -> {
            loadRewardedAd();
        });
        binding.btnShow.setOnClickListener(v -> {
            showAd();
        });
    }

    private GGRewardedAd mAd = null;

    private void loadRewardedAd() {
        mAd = new GGRewardedAd(this, "float-13571");

        mAd.addListener(new GGRewardedAdsEventListener() {
            @Override
            public void onReward() {
                Toast.makeText(MainActivity.this, "Reward given", Toast.LENGTH_SHORT).show();
                binding.tvStatus.setText("Rewarded");
            }

            @Override
            public void onAdLoaded() {
                binding.tvStatus.setText("Ad Loaded");
                binding.btnShow.setEnabled(true);
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                binding.tvStatus.setText("Ad Load Failed - " + adErrors.name());

                //Remove listener to prevent memory leaks
                mAd.removeListener(this);
            }

            @Override
            public void onAdShowFailed() {
                binding.tvStatus.setText("Ad Show Failed");

                //Remove listener to prevent memory leaks
                mAd.removeListener(this);
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