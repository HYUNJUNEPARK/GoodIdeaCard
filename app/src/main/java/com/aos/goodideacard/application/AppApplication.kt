package com.aos.goodideacard.application

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.aos.goodideacard.BuildConfig
import com.aos.goodideacard.datastore.AppDataStoreManager
import com.aos.goodideacard.features.exception.AppExceptionHandler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AppApplication: Application() {
    //이 범위 내에서 시작된 모든 코루틴은 애플리케이션이 살아 있는 동안 실행되고, 애플리케이션이 종료될 때 취소
    private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    @Inject
    lateinit var appDataStoreManager: AppDataStoreManager

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) Timber.plant(CustomDebugTree())

        setCrashHandler()
        observeAppBackground()
    }

    class CustomDebugTree: Timber.DebugTree() {
        override fun createStackElementTag(element: StackTraceElement): String {
            return "debugLog:${element.fileName}:${element.lineNumber}"
        }
    }

    private fun setCrashHandler() {
        Thread.setDefaultUncaughtExceptionHandler(AppExceptionHandler(this))
    }

    private fun observeAppBackground() {
        applicationScope.launch {
            appDataStoreManager.background.collect { themeCode ->
                AppCompatDelegate.setDefaultNightMode(themeCode)
            }
        }
    }
}