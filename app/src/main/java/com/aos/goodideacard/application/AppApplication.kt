package com.aos.goodideacard.application

import android.app.Application
import com.aos.goodideacard.BuildConfig
import com.aos.goodideacard.features.exception.AppExceptionHandler
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class AppApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(CustomDebugTree())
        setCrashHandler()
    }

    class CustomDebugTree: Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return "debugLog:${element.fileName}:${element.lineNumber}"
        }
    }

    private fun setCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler(this))
    }
}