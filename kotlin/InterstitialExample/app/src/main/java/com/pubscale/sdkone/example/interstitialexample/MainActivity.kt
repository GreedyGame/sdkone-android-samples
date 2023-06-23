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

        //Disabling auto app open ads
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false)

        with(binding) {
            btnLoad.setOnClickListener { loadInterstitialAd() }
            btnShow.setOnClickListener { showAd() }
        }
    }

    private val mAd = GGInterstitialAd(this, "float-13569")

    private fun loadInterstitialAd() {
        with(binding){
            tvStatus.text = "Ad Loading"

            mAd.addListener(object : GGInterstitialEventsListener {
                override fun onAdLoaded() {
                    tvStatus.text = "Ad Loaded"
                    btnShow.isEnabled = true
                }

                override fun onAdLoadFailed(adErrors: AdErrors) {
                    tvStatus.text = "Ad Load Failed - " + adErrors.name

                    //Remove listener to prevent memory leaks
                    mAd.removeListener(this)
                }

                override fun onAdShowFailed() {
                    tvStatus.text = "Ad Show Failed"

                    //Remove listener to prevent memory leaks
                    mAd.removeListener(this)
                }

                override fun onAdOpened() {
                    Toast.makeText(this@MainActivity, "Ad Opened", Toast.LENGTH_SHORT).show()
                    tvStatus.text = "Ad Opened"
                }

                override fun onAdClosed() {
                    tvStatus.text = "Ad Closed"
                    btnShow.isEnabled = false

                    //Remove listener to prevent memory leaks
                    mAd.removeListener(this)
                }
            })
        }

        //Load ad after adding a listener
        mAd.loadAd()
    }

    private fun showAd() {
        mAd.show(this)
    }
}