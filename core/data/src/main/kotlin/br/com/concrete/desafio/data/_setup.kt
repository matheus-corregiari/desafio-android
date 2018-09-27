package br.com.concrete.desafio.data

import android.app.Activity
import android.app.Application
import android.os.Bundle
import org.greenrobot.eventbus.EventBus
import timber.log.Timber

fun setupTimber(isDebug: Boolean) {
    if (isDebug)
        Timber.plant(Timber.DebugTree())
}

fun Application.setupEventBus() {
    registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
        override fun onActivityPaused(activity: Activity) = Unit

        override fun onActivityResumed(activity: Activity) = Unit

        override fun onActivityStarted(activity: Activity) {
            if (!EventBus.getDefault().isRegistered(activity))
                EventBus.getDefault().register(activity)
        }

        override fun onActivityDestroyed(activity: Activity) = Unit

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) = Unit

        override fun onActivityStopped(activity: Activity) {
            if (EventBus.getDefault().isRegistered(activity))
                EventBus.getDefault().unregister(activity)
        }

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit
    })
}