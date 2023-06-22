package com.pubscale.sdkone.example.recyclerviewadadapterexample;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.core.app_open_ads.general.GGAppOpenAds;
import com.pubscale.sdkone.core.helpers.recyclerview.RecyclerViewAdAdapter;
import com.pubscale.sdkone.example.recyclerviewadadapterexample.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Disabling auto app open ads
        GGAppOpenAds.setShouldShowOnAppMovedToForeground(false);

        ArrayList<String> list = prepareSampleList(35);

        RVAdapter rvAdapter = new RVAdapter(); //Recycler view Adapter
        rvAdapter.submitList(list); // Submit data list to the adapter

        //SDKOne's adapter for auto ads
        RecyclerViewAdAdapter recyclerViewAdAdapter =
                new RecyclerViewAdAdapter.Builder(this)
                        .setAdapter(rvAdapter) //Pass recyclerview adapter here
                        .setRepeatInterval(10) //Set count of items after which you must see an ad
                        .setFloatUnitId("float-13582") //Ad unit id
                        .setAdLayoutId(R.layout.recycler_ad_view)
                        .build(); // build the adapter

        binding.recyclerView.setAdapter(recyclerViewAdAdapter); //set the adapter to recycler view
    }

    private ArrayList<String> prepareSampleList(int size) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("Item " + (i + 1));
        }
        return list;
    }

}