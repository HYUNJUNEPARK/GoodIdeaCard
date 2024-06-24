package com.aos.goodideacard.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.aos.goodideacard.BuildConfig
import com.aos.goodideacard.datastore.AppDataStoreManager
import com.aos.goodideacard.features.exception.AppExceptionHandler
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AppApplication: Application() {
    //TODO 깔끔하게 바꿀 것!
    companion object {
        var blocKActivityResumeAction = false
    }

    @Inject
    lateinit var appDataStoreManager: AppDataStoreManager

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(CustomDebugTree())

        setCrashHandler()
        observeLiveData()
    }

    class CustomDebugTree: Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return "debugLog:${element.fileName}:${element.lineNumber}"
        }
    }

    private fun setCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler(this))
    }

    private fun observeLiveData() {
        //사용자 테마 코드 적용
        appDataStoreManager.appThemeLiveData.observeForever { themeCode ->
            AppCompatDelegate.setDefaultNightMode(themeCode)
        }
    }

}