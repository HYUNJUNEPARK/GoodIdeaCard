package com.aos.goodideacard.features.base

import androidx.fragment.app.DialogFragment
import com.aos.goodideacard.consts.AppConst
import timber.log.Timber

open class BaseDialogFragment: DialogFragment() {
    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onDestroy()")
    }
}