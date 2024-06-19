package com.aos.goodideacard.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import timber.log.Timber

object JsonUtil {
    val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    inline fun <reified T> serialize(data: T): String? {
        try {
            val jsonAdapter = moshi.adapter(T::class.java)
            return jsonAdapter.toJson(data)
        } catch (e:Exception) {
            Timber.e("Exception : $e")
            return null
        }
    }

    inline fun <reified T> deserialize(json: String): T? {
        try {
            val jsonAdapter = moshi.adapter(T::class.java)
            return jsonAdapter.fromJson(json)
        } catch (e: Exception) {
            Timber.e("Exception : $e")
            return null
        }
    }
}