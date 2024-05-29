package com.aos.goodideacard.features.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.aos.goodideacard.consts.AppConst
import timber.log.Timber

open class BaseFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onViewCreated()")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onResume()")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onPause()")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onDestroyView()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onDestroy()")
    }
}