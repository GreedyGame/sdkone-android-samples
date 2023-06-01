package com.pubscale.sdkone.example.bannerexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.core.adview.general.AdLoadCallback;
import com.pubscale.sdkone.core.models.general.AdErrors;
import com.pubscale.sdkone.example.bannerexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLoad.setOnClickListener(v -> {
            loadBannerAd();
        });
    }

    private void loadBannerAd() {
        binding.tvStatus.setText("Ad Loading..");
        binding.adView.setUnitId("float-13568"); //Your Ad Unit ID here
        binding.adView.setAdsMaxHeight(250); //Value is in pixels, not in dp

        binding.adView.loadAd(new AdLoadCallback() {
            @Override
            public void onAdLoaded() {
                binding.tvStatus.setText("Ad Loaded");
            }

            @Override
            public void onAdLoadFailed(@NonNull AdErrors adErrors) {
                binding.tvStatus.setText("Ad Load Failed - " + adErrors.name());
            }

            @Override
            public void onUiiOpened() {
                binding.tvStatus.setText("Ad Uii Opened");
            }

            @Override
            public void onUiiClosed() {
                binding.tvStatus.setText("Ad Uii Closed");
            }

            //onReadyForRefresh gets called only when refreshPolicy is set to MANUAL
            @Override
            public void onReadyForRefresh() {
                binding.tvStatus.setText("Ad ready for refresh");
            }
        });
    }
}