package com.pubscale.sdkone.example.recyclerviewadadapterexample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds
import com.pubscale.sdkone.core.helpers.recyclerview.RecyclerViewAdAdapter
import com.pubscale.sdkone.example.recyclerviewadadapterexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Disabling auto app open ads
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false)

        val list = prepareSampleList(35)

        val rvAdapter = RVAdapter() //Recycler view Adapter
        rvAdapter.submitList(list) // Submit data list to the adapter

        //SDKOne's adapter for auto ads
        val recyclerViewAdapter = RecyclerViewAdAdapter.Builder(this)
            .setAdapter(rvAdapter) //Pass recyclerview adapter here
            .setRepeatInterval(10) //Set count of items after which you must see an ad
            .setFloatUnitId("float-13582") //Ad unit id
            .setAdLayoutId(R.layout.recycler_ad_view) // Ad view layout id
            .build() // build the adapter

        binding.recyclerView.adapter = recyclerViewAdapter
    }

    private fun prepareSampleList(size: Int): ArrayList<String> {
        val list = ArrayList<String>()
        for (i in 1 .. size) {
            list.add("Item $i")
        }
        return list
    }
}