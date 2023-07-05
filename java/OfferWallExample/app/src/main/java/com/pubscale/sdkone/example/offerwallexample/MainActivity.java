package com.pubscale.sdkone.example.offerwallexample;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pubscale.sdkone.example.offerwallexample.databinding.ActivityMainBinding;
import com.pubscale.sdkone.offerwall.OfferWall;
import com.pubscale.sdkone.offerwall.OfferWallConfig;
import com.pubscale.sdkone.offerwall.models.OfferWallInitListener;
import com.pubscale.sdkone.offerwall.models.OfferWallListener;
import com.pubscale.sdkone.offerwall.models.Reward;
import com.pubscale.sdkone.offerwall.models.errors.InitError;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("ow_example", Context.MODE_PRIVATE);

        String balance = String.valueOf(sharedPreferences.getFloat("balance", 0f));
        binding.tvBalance.setText(balance);

        binding.init.setOnClickListener(view -> {
            setupGGOfferWall();
        });

        binding.launch.setOnClickListener(view -> {
            OfferWall.launch(this, ggOfferWallListener);
        });

        binding.destroy.setOnClickListener(view -> {
            OfferWall.destroy();
            binding.destroy.setEnabled(false);
        });
    }

    private void setupGGOfferWall() {
        String uniqueIdTyped = binding.uniqueIdText.getText().toString();
        String appKey = "f53e007b-a86c-436d-a7d3-40176ccab173";

        OfferWallConfig offerWallConfig = new OfferWallConfig.Builder(getApplicationContext(), appKey)
                .setTitle("Title of your offer wall")
                .setUniqueId(uniqueIdTyped)
                .build();

        OfferWall.init(offerWallConfig, new OfferWallInitListener() {
            @Override
            public void onInitSuccess() {
                binding.destroy.setEnabled(true);
                Toast.makeText(getApplicationContext(), "OfferWall SDK Initialed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInitFailed(InitError initError) {
                Toast.makeText(getApplicationContext(), "OfferWall SDK Init Failed: "+ initError, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private OfferWallListener ggOfferWallListener = new OfferWallListener() {
        @Override
        public void onOfferWallShowed() {
            Toast.makeText(getApplicationContext(), "Offer wall showed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onOfferWallClosed() {
            Toast.makeText(getApplicationContext(), "Offer wall closed", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRewardClaimed(Reward reward) {
            float balance = sharedPreferences.getFloat("balance", 0f);
            balance += reward.getAmount();

            binding.tvBalance.setText(String.valueOf(balance));
            sharedPreferences.edit().putFloat("balance", balance).apply();

            Toast.makeText(
                    getApplicationContext(),
                    "Offer wall reward claimed: " + reward.getAmount() + " " + reward.getCurrency(),
                    Toast.LENGTH_SHORT
            ).show();
        }

        @Override
        public void onFailed(String message) {
            Toast.makeText(getApplicationContext(), "onFailed: " + message, Toast.LENGTH_SHORT).show();
        }
    };
}