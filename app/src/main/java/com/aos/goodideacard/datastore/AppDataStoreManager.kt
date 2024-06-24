package com.aos.goodideacard.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.aos.goodideacard.enums.BackgroundType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.io.IOException
import java.util.UUID

/**
 * 사용자 설정을 저장하는 DataStore
 */
class AppDataStoreManager(private val context: Context) {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "pref_settings")

    private val appThemeKey = intPreferencesKey("appThemeKey")
    private val uuidKey = stringPreferencesKey("uuidKey")

    /**
     * 카드팩 아이디 생성에 사용되는 앱 고유 uuid
     */
    suspend fun getUUID(): String {
        val uuid = context.dataStore.data.map { preferences ->
            preferences[uuidKey]
        }.first()

        val newUUID = if (uuid.isNullOrEmpty()) {
            val randomUUID = UUID.randomUUID().toString()
            Timber.i("UUID 생성 : $randomUUID")

            context.dataStore.edit { preferences ->
                preferences[uuidKey] = randomUUID
            }

            randomUUID
        } else {
            Timber.i("기존 UUID 로드 : $uuid")
            uuid
        }

        return newUUID
    }

    /**
     * 사용자 테마 설정 데이터(시스템, 라이트, 다크모드)
     */
    val background: Flow<Int> = context.dataStore.data
        .catch {exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                Timber.e("Exception(appTheme):$exception")
                throw exception
            }
        }
        .map { preference ->
            preference[appThemeKey] ?: BackgroundType.SYSTEM.code //기본 값 : '시스템 설정 모드'
        }
        .distinctUntilChanged()

    suspend fun saveBackground(code: BackgroundType) {
        Timber.i("사용자 테마 설정 저장 : $code")
        context.dataStore.edit { preference ->
            preference[appThemeKey] = code.code
        }
    }

    /**
     * DataStore 초기화
     */
    suspend fun clear() {
        Timber.e("DataStore 초기화(유저 설정 삭제)")
        context.dataStore.edit { preference ->
            preference.clear()
        }
    }
}