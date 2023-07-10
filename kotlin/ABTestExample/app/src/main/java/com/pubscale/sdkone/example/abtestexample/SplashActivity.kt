package com.pubscale.sdkone.example.abtestexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.example.abtestexample.ad_formats.AppOpen
import com.pubscale.sdkone.example.abtestexample.databinding.ActivitySplashBinding
import com.pubscale.sdkone.example.abtestexample.event_listener.AppOpenAdEventListener

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAppOpenAd()
    }

    private fun loadAppOpenAd() {
        val appOpenAdEventListener: AppOpenAdEventListener = object : AppOpenAdEventListener {
            override fun onAdLoading() {
                binding.appOpenStatus.text = "Loading..."
            }

            override fun onAdLoaded() {
                binding.appOpenStatus.text = "Loaded"
                AppOpen.showAd(this@SplashActivity)
            }

            override fun onAdLoadFailed() {
                binding.appOpenStatus.text = "Failed"
                openMainActivity()
            }

            override fun onAdShowFailed() {
                binding.appOpenStatus.text = "Show failed"
                openMainActivity()
            }

            override fun onAdOpened() {
                binding.appOpenStatus.text = "Opened"
            }

            override fun onAdClosed() {
                binding.appOpenStatus.text = "Closed"
                openMainActivity()
            }

            override fun onAdDisabled() {
                binding.appOpenStatus.text = "Disabled"
                Toast.makeText(this@SplashActivity, "App open ad disabled", Toast.LENGTH_SHORT)
                    .show()
                openMainActivity()
            }
        }
        AppOpen.setAppOpenAdEventListener(appOpenAdEventListener).loadAd(this)
    }

    private fun openMainActivity() {
        startActivity(
            Intent(this@SplashActivity, MainActivity::class.java)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
    }
}