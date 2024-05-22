package com.aos.goodideacard.application

import android.app.Application
import com.aos.goodideacard.BuildConfig
import timber.log.Timber

class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(CustomDebugTree())
    }

    class CustomDebugTree: Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return "debugLog:${element.fileName}:${element.lineNumber}"
        }
    }
}