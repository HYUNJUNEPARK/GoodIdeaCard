package com.aos.goodideacard.features.base

import androidx.appcompat.app.AppCompatActivity
import com.aos.goodideacard.consts.AppConst
import timber.log.Timber

open class BaseActivity : AppCompatActivity() {
//    /**
//     * 사용자 언어 설정을 적용하기 위해 추가
//     */
//    override fun attachBaseContext(newBase: Context?) {
//        super.attachBaseContext(LocaleWrapper.wrap(newBase!!))
//    }

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

//    fun changeLanguage(language: Language) {
//
//        val lang = if (language == Language.DEFAULT) {
//            Locale.getDefault().language
//        } else {
//            language.value
//        }
//
//        Timber.i("사용자 언어 설정 : Language($language, $lang)")
//
//        LocaleWrapper.setLocale(lang)
//        recreate()
//    }
}