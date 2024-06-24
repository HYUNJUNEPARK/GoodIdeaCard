package com.aos.goodideacard.util

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.aos.goodideacard.R
import java.io.Serializable

object Extension {
    /**
     * API 33 이후 getSerializable() deprecated
     */
    @Suppress("UNCHECKED_CAST")
    fun <T: Serializable> Intent.intentSerializable(key: String, clazz: Class<T>): T? {
        return if (Build.VERSION.SDK_INT >= 33) {
            this.getSerializableExtra(key, clazz)
        } else {
            this.getSerializableExtra(key) as T?
        }
    }

    /**
     * colors.xml에 위치한 컬러 리소스를 컬러 스티링(ex. "#FFFFFF") 형태로 가져온다.
     * Int : R.color.app_theme
     */
    fun Int.colorString(context: Context): String {
        val colorInt = ContextCompat.getColor(context, this)
        val colorString = String.format("#%06X", 0xFFFFFF and colorInt)
        return colorString
    }

//    /**
//     * DTO 를 번들로 전달할 수 있게하는 번들 확장 함수
//     */
//    @Suppress("DEPRECATION")
//    inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
//        Build.VERSION.SDK_INT >= 33 -> getParcelable(key, T::class.java)
//        else -> getParcelable(key) as? T
//    }
}