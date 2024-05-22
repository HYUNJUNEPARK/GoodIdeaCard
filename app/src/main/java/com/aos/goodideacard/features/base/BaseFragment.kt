package com.aos.goodideacard.features.base

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import timber.log.Timber

open class BaseFragment: Fragment() {
    protected val constDelayMillis = 2000L
    protected var backPressedTime = 0L

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

    /**
     * 2초 내에 클릭을 백버튼을 두번 눌러야 앱이 종료되는 콜백
     */
    fun doubleBackPressedCallback(activity: Activity) : OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTimeMillis = System.currentTimeMillis()
                if (currentTimeMillis > backPressedTime + constDelayMillis) {
                    backPressedTime = currentTimeMillis
                    Toast.makeText(activity.applicationContext, activity.applicationContext.getString(R.string.msg_app_close_msg), Toast.LENGTH_SHORT).show()
                } else if (currentTimeMillis <= backPressedTime + constDelayMillis) {
                    activity.finish()
                }
            }
        }
    }
}