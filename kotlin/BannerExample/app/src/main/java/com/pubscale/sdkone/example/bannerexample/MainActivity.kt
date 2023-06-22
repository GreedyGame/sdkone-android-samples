package com.pubscale.sdkone.example.bannerexample

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.core.adview.general.AdLoadCallback
import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds
import com.pubscale.sdkone.core.models.general.AdErrors
import com.pubscale.sdkone.example.bannerexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Disabling auto app open ads
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false)

        with(binding) {
            btnLoad.setOnClickListener { loadNativeAd() }
        }

    }

    private fun loadNativeAd() {
        with(binding) {
            tvStatus.text = "Ad Loading.."
            adView.unitId = "float-13568" //Your Ad Unit ID here
            adView.adsMaxHeight =
                dpToPx(this@MainActivity, 300f).toInt() //Value is in pixels, not in dp
            adView.loadAd(object : AdLoadCallback {
                override fun onAdLoaded() {
                    tvStatus.text = "Ad Loaded"
                }

                override fun onAdLoadFailed(adErrors: AdErrors) {
                    tvStatus.text = "Ad Load Failed - " + adErrors.name
                }

                override fun onUiiOpened() {
                    tvStatus.text = "Ad Uii Opened"
                }

                override fun onUiiClosed() {
                    tvStatus.text = "Ad Uii Closed"
                }

                //onReadyForRefresh gets called only when refreshPolicy is set to MANUAL
                override fun onReadyForRefresh() {
                    tvStatus.text = "Ad ready for refresh"
                }
            })
        }
    }

    private fun dpToPx(context: Context, dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }
}