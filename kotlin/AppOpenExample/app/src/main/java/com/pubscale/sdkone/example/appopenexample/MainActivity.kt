package com.pubscale.sdkone.example.appopenexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.core.rewarded_ad.general.GGRewardedAd
import com.pubscale.sdkone.core.rewarded_ad.general.GGRewardedAdsEventListener
import com.pubscale.sdkone.example.appopenexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.switchAppOpen.setOnCheckedChangeListener { _, isChecked ->
            GGAppOpenAds.INSTANCE.shouldShowOnAppMovedToForeground = isChecked
        }
    }

}