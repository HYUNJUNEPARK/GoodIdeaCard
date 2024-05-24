package com.aos.goodideacard.features.base

import androidx.lifecycle.ViewModel
import com.aos.goodideacard.consts.AppConst
import timber.log.Timber

open class BaseViewModel : ViewModel() {
    init {
        Timber.tag(AppConst.LOG_TAG_VIEW_MODEL).i("${javaClass.simpleName} init")
    }

    override fun onCleared() {
        super.onCleared()
        Timber.tag(AppConst.LOG_TAG_VIEW_MODEL).i("${javaClass.simpleName} onCleared()")
    }
}