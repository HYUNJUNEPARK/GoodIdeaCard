package com.aos.goodideacard.features.exception

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.os.Process
import kotlin.system.exitProcess

class AppExceptionHandler(private val application: Application) : Thread.UncaughtExceptionHandler {
    companion object {
        const val EXTRA_ERROR = "EXTRA_ERROR"
    }

    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        startErrorActivity(throwable)

        /*해당 코드를 사용해보려고 했지만 Firebase Crashlytics 에는 정상적으로 리포팅되지만 앱크래시 발생
          Throwable 객체 자체를 ErrorActivity 로 전달한 뒤 ErrorActivity 에서 Crashlytics 에 리포팅하는 방법으로 우회*/
        //crashlyticsExceptionHandler?.uncaughtException(thread, throwable)
        Process.killProcess(Process.myPid())
        exitProcess(-1)
    }

    private fun startErrorActivity(throwable: Throwable) {
        val extras = Bundle().apply {
            putSerializable(EXTRA_ERROR, throwable)
        }

        val intent = Intent(application, ErrorActivity::class.java).apply {
            putExtras(extras)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }

        application.startActivity(intent)
    }
}