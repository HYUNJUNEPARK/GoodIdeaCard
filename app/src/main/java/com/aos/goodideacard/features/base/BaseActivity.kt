package com.aos.goodideacard.features.base

import androidx.appcompat.app.AppCompatActivity
import com.aos.goodideacard.consts.AppConst
import timber.log.Timber

open class BaseActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onResume()")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onPause()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onDestroy()")
    }
}