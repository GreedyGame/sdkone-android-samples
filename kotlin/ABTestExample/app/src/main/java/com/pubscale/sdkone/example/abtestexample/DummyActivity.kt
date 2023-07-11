package com.pubscale.sdkone.example.abtestexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.example.abtestexample.databinding.ActivityDummyBinding

class DummyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDummyBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDummyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeButton.setOnClickListener { onBackPressed() }
    }
}