package com.pubscale.sdkone.example.abtestexample

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.example.abtestexample.ad_formats.AppOpen
import com.pubscale.sdkone.example.abtestexample.ad_formats.Banner
import com.pubscale.sdkone.example.abtestexample.ad_formats.Interstitial
import com.pubscale.sdkone.example.abtestexample.ad_formats.Native
import com.pubscale.sdkone.example.abtestexample.ad_formats.Rewarded
import com.pubscale.sdkone.example.abtestexample.databinding.ActivityMainBinding
import com.pubscale.sdkone.example.abtestexample.event_listener.AppOpenAdEventListener
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener
import com.pubscale.sdkone.example.abtestexample.event_listener.InterstitialAdEventListener
import com.pubscale.sdkone.example.abtestexample.event_listener.RewardedAdEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.appOpenLoadButton.setOnClickListener { loadAppOpenAd() }

        binding.appOpenShowButton.setOnClickListener { showAppOpenAd() }

        binding.bannerAdLoadButton.setOnClickListener { loadBannerAd() }

        binding.nativeAdLoadButton.setOnClickListener { loadNativeAd() }

        binding.interstitialLoadButton.setOnClickListener { loadInterstitialAd(false) }

        binding.interstitialShowButton.setOnClickListener { showInterstitialAd() }

        binding.goToNextActivityButton.setOnClickListener { loadInterstitialAd(true) }

        binding.rewardedLoadButton.setOnClickListener { loadRewardedAd() }

        binding.rewardedShowButton.setOnClickListener { showRewardedAd() }

    }

    private fun loadAppOpenAd() {
        val appOpenAdEventListener = object : AppOpenAdEventListener {
            override fun onAdLoading() {
                binding.appOpenAdStatus.text = "Loading..."
                binding.appOpenLoadButton.isEnabled = false
            }

            override fun onAdLoaded() {
                binding.appOpenAdStatus.text = "Loaded"
                binding.appOpenShowButton.isEnabled = true

            }

            override fun onAdLoadFailed() {
                binding.appOpenAdStatus.text = "Failed"
                binding.appOpenLoadButton.isEnabled = true
            }

            override fun onAdShowFailed() {
                binding.appOpenAdStatus.text = "Show failed"
                binding.appOpenShowButton.isEnabled = false
                binding.appOpenLoadButton.isEnabled = true
            }

            override fun onAdOpened() {
                binding.appOpenAdStatus.text = "Opened"
                binding.appOpenShowButton.isEnabled = false
                binding.appOpenLoadButton.isEnabled = true
            }

            override fun onAdClosed() {
                binding.appOpenAdStatus.text = "Closed"
            }
        }

        AppOpen.setAppOpenAdEventListener(appOpenAdEventListener).loadAd(this, true)
    }

    private fun showAppOpenAd() {
        AppOpen.showAd(this, true)
    }

    private fun loadBannerAd() {
        val bannerNativeAdEventListener = object : BannerNativeAdEventListener {
            override fun onAdLoading() {
                binding.bannerAdStatus.text = "Loading..."
                binding.bannerAdLoadButton.isEnabled = false
            }

            override fun onAdLoaded() {
                binding.bannerAdStatus.text = "Loaded"
                binding.bannerAdLoadButton.isEnabled = true
            }

            override fun onAdLoadFailed() {
                binding.bannerAdStatus.text = "Failed"
                binding.bannerAdLoadButton.isEnabled = true
            }

            override fun onUiiOpened() {
                binding.bannerAdStatus.text = "Opened"
            }

            override fun onUiiClosed() {
                binding.bannerAdStatus.text = "Closed"
            }

            override fun onReadyForRefresh() {
                binding.bannerAdStatus.text = "Ready for refresh"
            }
        }
        Banner.setBannerNativeAdEventListener(bannerNativeAdEventListener)
            .loadAd(this, binding.bannerAd)
    }

    private fun loadNativeAd() {
        val bannerNativeAdEventListener = object : BannerNativeAdEventListener {
            override fun onAdLoading() {
                binding.nativeAdStatus.text = "Loading..."
                binding.nativeAdLoadButton.isEnabled = false
            }

            override fun onAdLoaded() {
                binding.nativeAdStatus.text = "Loaded"
                binding.nativeAdLoadButton.isEnabled = true
            }

            override fun onAdLoadFailed() {
                binding.nativeAdStatus.text = "Failed"
                binding.nativeAdLoadButton.isEnabled = true
            }

            override fun onUiiOpened() {
                binding.nativeAdStatus.text = "Opened"
            }

            override fun onUiiClosed() {
                binding.nativeAdStatus.text = "Closed"
            }

            override fun onReadyForRefresh() {
                binding.nativeAdStatus.text = "Ready for refresh"
            }
        }
        Native.setBannerNativeAdEventListener(bannerNativeAdEventListener)
            .loadAd(this, binding.nativeAd)
    }

    private fun loadInterstitialAd(goToNextActivity: Boolean) {
        val interstitialAdEventListener = object : InterstitialAdEventListener {
            override fun onAdLoading() {
                binding.interstitialAdStatus.text = "Loading..."
                binding.interstitialLoadButton.isEnabled = false
            }

            override fun onAdLoaded() {
                binding.interstitialAdStatus.text = "Loaded"
                if (goToNextActivity) showInterstitialAd() else binding.interstitialShowButton.isEnabled =
                    true
            }

            override fun onAdLoadFailed() {
                binding.interstitialAdStatus.text = "Load failed"
                binding.interstitialLoadButton.isEnabled = true
                if (goToNextActivity) proceedToNextActivity()
            }

            override fun onAdShowFailed() {
                binding.interstitialAdStatus.text = "Show failed"
                binding.interstitialShowButton.isEnabled = false
                binding.interstitialLoadButton.isEnabled = true
                if (goToNextActivity) proceedToNextActivity()
            }

            override fun onAdOpened() {
                binding.interstitialAdStatus.text = "Opened"
                binding.interstitialShowButton.isEnabled = false
                binding.interstitialLoadButton.isEnabled = true
            }

            override fun onAdClosed() {
                binding.interstitialAdStatus.text = "Closed"
                if (goToNextActivity) proceedToNextActivity()
            }
        }
        Interstitial.setInterstitialAdEventListener(interstitialAdEventListener).loadAd(this)
    }

    private fun showInterstitialAd() {
        Interstitial.showAd(this)
    }

    private fun proceedToNextActivity() {
        startActivity(Intent(this, DummyActivity::class.java))
    }

    private fun loadRewardedAd() {
        val rewardedAdEventListener = object : RewardedAdEventListener {
            override fun onReward() {
                binding.rewardedAdStatus.text = "Rewarded"
                Toast.makeText(applicationContext, "Rewarded", Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoading() {
                binding.rewardedAdStatus.text = "Loading..."
                binding.rewardedLoadButton.isEnabled = false
            }

            override fun onAdLoaded() {
                binding.rewardedAdStatus.text = "Loaded"
                binding.rewardedShowButton.isEnabled = true
            }

            override fun onAdLoadFailed() {
                binding.rewardedAdStatus.text = "Failed"
                binding.rewardedLoadButton.isEnabled = true
            }

            override fun onAdShowFailed() {
                binding.rewardedAdStatus.text = "Show failed"
                binding.rewardedShowButton.isEnabled = false
                binding.rewardedLoadButton.isEnabled = true
            }

            override fun onAdOpened() {
                binding.rewardedAdStatus.text = "Opened"
                binding.rewardedShowButton.isEnabled = false
                binding.rewardedLoadButton.isEnabled = true
            }

            override fun onAdClosed() {
                binding.rewardedAdStatus.text = "Closed"
            }
        }
        Rewarded.setRewardedAdEventListener(rewardedAdEventListener).loadAd(this)
    }

    private fun showRewardedAd() {
        Rewarded.showAd(this)
    }
}