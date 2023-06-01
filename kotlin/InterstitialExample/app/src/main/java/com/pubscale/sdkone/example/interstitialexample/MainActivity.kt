package com.pubscale.sdkone.example.interstitialexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds
import com.pubscale.sdkone.core.interstitial.general.GGInterstitialAd
import com.pubscale.sdkone.core.interstitial.general.GGInterstitialEventsListener
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.example.interstitialexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GGAppOpenAds.INSTANCE.shouldShowOnAppMovedToForeground = false

        binding.btnLoad.setOnClickListener { loadInterstitialAd() }
        binding.btnShow.setOnClickListener { showAd() }
    }

    private val mAd = GGInterstitialAd(this, "float-13569")

    private fun loadInterstitialAd() {
        mAd.addListener(object : GGInterstitialEventsListener {
            override fun onAdLoaded() {
                binding.tvStatus.text = "Ad Loaded"
                binding.btnShow.isEnabled = true
            }

            override fun onAdLoadFailed(adErrors: AdErrors) {
                binding.tvStatus.text = "Ad Load Failed - " + adErrors.name

                //Remove listener to prevent memory leaks
                mAd.removeListener(this)
            }

            override fun onAdShowFailed() {
                binding.tvStatus.text = "Ad Show Failed"

                //Remove listener to prevent memory leaks
                mAd.removeListener(this)
            }

            override fun onAdOpened() {
                Toast.makeText(this@MainActivity, "Ad Opened", Toast.LENGTH_SHORT).show()
                binding.tvStatus.text = "Ad Opened"
            }

            override fun onAdClosed() {
                binding.tvStatus.text = "Ad Closed"
                binding.btnShow.isEnabled = false

                //Remove listener to prevent memory leaks
                mAd.removeListener(this)
            }
        })

        //Load ad after adding a listener

        //Load ad after adding a listener
        mAd.loadAd()
    }

    private fun showAd() {
        mAd.show(this)
    }
}