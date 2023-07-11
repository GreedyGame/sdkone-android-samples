package com.pubscale.sdkone.example.offerwallexample

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.example.offerwallexample.databinding.ActivityMainBinding
import com.pubscale.sdkone.offerwall.OfferWall
import com.pubscale.sdkone.offerwall.OfferWallConfig
import com.pubscale.sdkone.offerwall.models.OfferWallInitListener
import com.pubscale.sdkone.offerwall.models.OfferWallListener
import com.pubscale.sdkone.offerwall.models.Reward
import com.pubscale.sdkone.offerwall.models.errors.InitError

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("ow_example", Context.MODE_PRIVATE)

        val balance = sharedPreferences.getFloat("balance", 0f).toString()
        binding.tvBalance.text = balance

        binding.init.setOnClickListener {
            setupGGOfferWall()
        }

        binding.launch.setOnClickListener {
            OfferWall.launch(this, ggOfferWallListener)
        }

        binding.destroy.setOnClickListener {
            OfferWall.destroy()
            binding.destroy.isEnabled = false
        }
    }

    private fun setupGGOfferWall() {
        val uniqueIdTyped = binding.uniqueIdText.text.toString()
        val appKey = "f53e007b-a86c-436d-a7d3-40176ccab173"

        val offerWallConfig = OfferWallConfig.Builder(this, appKey)
            .setTitle("Title of your offer wall")
            .setUniqueId(uniqueIdTyped)
            .build()

        OfferWall.init(offerWallConfig, object: OfferWallInitListener {
            override fun onInitSuccess() {
                binding.destroy.isEnabled = true
                Toast.makeText(this@MainActivity, "OfferWall SDK Initialed", Toast.LENGTH_SHORT).show()
            }

            override fun onInitFailed(error: InitError) {
                Toast.makeText(this@MainActivity,"OfferWall SDK Init Failed: $error",Toast.LENGTH_SHORT).show()
            }
        })
    }

    private val ggOfferWallListener = object: OfferWallListener {
        override fun onOfferWallShowed() {
            Toast.makeText(this@MainActivity, "Offer wall showed", Toast.LENGTH_SHORT).show()
        }

        override fun onOfferWallClosed() {
            Toast.makeText(this@MainActivity, "Offer wall closed", Toast.LENGTH_SHORT).show()
        }

        override fun onRewardClaimed(reward: Reward) {
            var balance = sharedPreferences.getFloat("balance", 0f)
            balance += reward.amount

            binding.tvBalance.text = balance.toString()
            sharedPreferences.edit().putFloat("balance", balance).apply()

            Toast.makeText(
                this@MainActivity,
                "Offer wall reward claimed: ${reward.amount} ${reward.currency}",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onFailed(message: String) {
            Toast.makeText(this@MainActivity, "onFailed: $message", Toast.LENGTH_SHORT).show()
        }
    }
}