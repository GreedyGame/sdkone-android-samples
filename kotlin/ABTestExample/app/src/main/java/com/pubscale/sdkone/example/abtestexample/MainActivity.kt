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
import com.pubscale.sdkone.example.abtestexample.event_listener.BannerNativeAdEventListener
import com.pubscale.sdkone.example.abtestexample.event_listener.InterstitialAdEventListener
import com.pubscale.sdkone.example.abtestexample.event_listener.RewardedAdEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bannerAdLoadButton.setOnClickListener { loadBannerAd() }

        binding.nativeAdLoadButton.setOnClickListener { loadNativeAd() }

        binding.interstitialLoadButton.setOnClickListener { loadInterstitialAd(false) }

        binding.interstitialShowButton.setOnClickListener { showInterstitialAd() }

        binding.goToNextActivityButton.setOnClickListener { loadInterstitialAd(true) }

        binding.rewardedLoadButton.setOnClickListener { loadRewardedAd() }

        binding.rewardedShowButton.setOnClickListener { showRewardedAd() }

        binding.switchAppOpen.setOnCheckedChangeListener { view, isChecked ->
            AppOpen.disableAppOpenAd(
                isChecked
            )
        }
    }

    private fun loadBannerAd() {
        val bannerNativeAdEventListener: BannerNativeAdEventListener =
            object : BannerNativeAdEventListener {
                override fun onAdLoading() {
                    binding.bannerAdStatus.text = "Loading..."
                }

                override fun onAdLoaded() {
                    binding.bannerAdStatus.text = "Loaded"
                }

                override fun onAdLoadFailed() {
                    binding.bannerAdStatus.text = "Failed"
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
        val bannerNativeAdEventListener: BannerNativeAdEventListener =
            object : BannerNativeAdEventListener {
                override fun onAdLoading() {
                    binding.nativeAdStatus.text = "Loading..."
                }

                override fun onAdLoaded() {
                    binding.nativeAdStatus.text = "Loaded"
                }

                override fun onAdLoadFailed() {
                    binding.nativeAdStatus.text = "Failed"
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
        val interstitialAdEventListener: InterstitialAdEventListener =
            object : InterstitialAdEventListener {
                override fun onAdLoading() {
                    binding.interstitialAdStatus.text = "Loading..."
                }

                override fun onAdLoaded() {
                    binding.interstitialAdStatus.text = "Loaded"
                    if (goToNextActivity) showInterstitialAd() else binding.interstitialShowButton.isEnabled =
                        true
                }

                override fun onAdLoadFailed() {
                    binding.interstitialAdStatus.text = "Load failed"
                    if (goToNextActivity) proceedToNextActivity()
                }

                override fun onAdShowFailed() {
                    binding.interstitialAdStatus.text = "Show failed"
                    binding.interstitialShowButton.isEnabled = false
                    if (goToNextActivity) proceedToNextActivity()
                }

                override fun onAdOpened() {
                    binding.interstitialAdStatus.text = "Opened"
                    binding.interstitialShowButton.isEnabled = false
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
        val rewardedAdEventListener: RewardedAdEventListener = object : RewardedAdEventListener {
            override fun onReward() {
                binding.rewardedAdStatus.text = "Rewarded"
                Toast.makeText(applicationContext, "Rewarded", Toast.LENGTH_SHORT).show()
            }

            override fun onAdLoading() {
                binding.rewardedAdStatus.text = "Loading..."
            }

            override fun onAdLoaded() {
                binding.rewardedAdStatus.text = "Loaded"
                binding.rewardedShowButton.isEnabled = true
            }

            override fun onAdLoadFailed() {
                binding.rewardedAdStatus.text = "Failed"
            }

            override fun onAdShowFailed() {
                binding.rewardedAdStatus.text = "Show failed"
                binding.rewardedShowButton.isEnabled = false
            }

            override fun onAdOpened() {
                binding.rewardedAdStatus.text = "Opened"
                binding.rewardedShowButton.isEnabled = false
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