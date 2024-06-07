package com.aos.goodideacard.util

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import java.util.Locale

/**
 * 다국어 지원 기능 구현에 사용하는 유틸
 */
object LocaleWrapper {
    private var sLocale: Locale? = null

    fun wrap(base: Context): Context {
        sLocale?.let { locale ->
            val res: Resources = base.resources
            val config: Configuration = res.configuration
            config.setLocale(locale)
            return base.createConfigurationContext(config)
        }
        return base
    }

    fun setLocale(lang: String) {
        sLocale = Locale(lang)
    }
}