package com.pubscale.sdkone.example.abtestexample.utils

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.pubscale.sdkone.example.abtestexample.ad_formats.AppOpen
import com.pubscale.sdkone.example.abtestexample.event_listener.AppOpenAdEventListener

class AppOpenAdManager : Application.ActivityLifecycleCallbacks, LifecycleObserver {

    private var currentActivity: Activity? = null
    private var appStarting = true
    private val appOpenAdEventListener = object : AppOpenAdEventListener {
        override fun onAdLoading() {
        }

        override fun onAdLoaded() {
            currentActivity?.let{ AppOpen.showAd(it, false) }
        }

        override fun onAdLoadFailed() {
        }

        override fun onAdShowFailed() {
        }

        override fun onAdOpened() {
        }

        override fun onAdClosed() {
        }

    }

    init {
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onMoveToForeground() {
        if(appStarting) {
            appStarting = false
            return
        }
        currentActivity?.let {
            AppOpen.setAppOpenAdEventListener(appOpenAdEventListener).loadAd(it, false)
        }
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
        if(!AppOpen.isShowingAd) currentActivity = activity
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}