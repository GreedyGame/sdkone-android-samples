package com.pubscale.sdkone.example.appopenexample

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.core.app_open_ads.general.AppOpenAdsEventsListener
import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.example.appopenexample.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAppOpenAds()
    }

    private fun loadAppOpenAds() {
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(true)
        GGAppOpenAds.addListener(object : AppOpenAdsEventsListener {
            override fun onAdLoaded() {
                GGAppOpenAds.show(this@SplashActivity)
            }

            override fun onAdLoadFailed(adErrors: AdErrors) {
                GGAppOpenAds.removeListener(this)
                openMainActivity()
            }

            override fun onAdShowFailed() {
                GGAppOpenAds.removeListener(this)
                openMainActivity()
            }

            override fun onAdOpened() {}
            override fun onAdClosed() {
                GGAppOpenAds.removeListener(this)
                openMainActivity()
            }
        })
        GGAppOpenAds.loadAd("float-13570")
    }

    private fun openMainActivity() {
        startActivity(
            Intent(
                this@SplashActivity,
                MainActivity::class.java
            ).setFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            )
        )
    }
}