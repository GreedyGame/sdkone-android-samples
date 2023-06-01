package com.pubscale.sdkone.example.appopenexample;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds;
import com.pubscale.sdkone.example.appopenexample.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.switchAppOpen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            GGAppOpenAds.INSTANCE.setShouldShowOnAppMovedToForeground(isChecked);
        });
    }
}