package com.pubscale.sdkone.example.abtestexample.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.ProcessLifecycleOwner;

import com.pubscale.sdkone.example.abtestexample.ad_formats.AppOpen;
import com.pubscale.sdkone.example.abtestexample.event_listener.AppOpenAdEventListener;

public class AppOpenAdManager implements Application.ActivityLifecycleCallbacks, LifecycleObserver {

    private Activity currentActivity = null;
    private boolean appStarting = true;
    private AppOpen appOpen = AppOpen.getInstance();
    private AppOpenAdEventListener appOpenAdEventListener = new AppOpenAdEventListener() {
        @Override
        public void onAdLoading() {

        }

        @Override
        public void onAdLoaded() {
            if(currentActivity != null) {
                appOpen.showAd(currentActivity, true);
            }
        }

        @Override
        public void onAdLoadFailed() {
        }

        @Override
        public void onAdShowFailed() {
        }

        @Override
        public void onAdOpened() {
        }

        @Override
        public void onAdClosed() {
        }
    };

    public AppOpenAdManager() {
        ProcessLifecycleOwner.get().getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onMoveToForeground() {
        if(appStarting) {
            appStarting = false;
            return;
        }
        if(currentActivity == null) return;
        appOpen.setAppOpenAdEventListener(appOpenAdEventListener).loadAd(currentActivity, true);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        if(!appOpen.isShowingAd()) currentActivity = activity;
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }
}
