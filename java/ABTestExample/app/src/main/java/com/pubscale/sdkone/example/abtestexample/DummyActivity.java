package com.pubscale.sdkone.example.abtestexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.pubscale.sdkone.example.abtestexample.databinding.ActivityDummyBinding;

public class DummyActivity extends AppCompatActivity {

    private ActivityDummyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDummyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.homeButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}