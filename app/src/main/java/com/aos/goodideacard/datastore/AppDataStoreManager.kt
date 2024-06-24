package com.aos.goodideacard.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.aos.goodideacard.enums.BackgroundType
import com.aos.goodideacard.enums.Language
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
    //private val languageKey = stringPreferencesKey("languageKey")

    //    private val iconTypeKey = intPreferencesKey("iconTypeKey")
//    private val iconTypeSettingHistoryKey = booleanPreferencesKey("iconTypeHistoryKey")
//    private val pinKey = stringPreferencesKey("pinKey")
//    private val authTypeKey = intPreferencesKey("authTypeKey")
//    private val securityStrengthKey = booleanPreferencesKey("securityStrengthKey")
//    private val screenShotKey = booleanPreferencesKey("screenShotKey")
//    private val browserConnectDialogKey = booleanPreferencesKey("browserConnectDialogKey")

//    private val introPageSettingKey = booleanPreferencesKey("readGuidePageKey")
//    private val lockStageDataKey = stringPreferencesKey("unlockTimeKey")
//    private val authTryCountKey = intPreferencesKey("authTryCountKey")
//    private val sortKey = stringPreferencesKey("sortKey")

    /**
     * 사용자 언어 설정
     */
//    suspend fun getLanguage(): Language {
//        val lang = context.dataStore.data.map { preferences ->
//            preferences[languageKey] ?: Language.DEFAULT.value
//        }.first()
//
//        val language = when(lang) {
//            Language.DEFAULT.value -> Language.DEFAULT
//            Language.KOREAN.value -> Language.KOREAN
//            Language.ENGLISH.value -> Language.ENGLISH
//            else -> {
//                Timber.e("Exception:Not handling this language type($lang)")
//                Language.DEFAULT
//            }
//        }
//
//        Timber.i("사용자 언어 설정 로드 : $language")
//        return language
//    }

//    suspend fun saveLanguage(language: Language) {
//        val lang = language.value
//
//        context.dataStore.edit { preferences ->
//            Timber.i("사용자 언어 설정 저장 : $lang")
//            preferences[languageKey] = lang
//        }
//    }

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
     * DataStore 초기화
     */
    suspend fun clear() {
        Timber.e("DataStore 초기화(유저 설정 삭제)")
        context.dataStore.edit { preference ->
            preference.clear()
        }
    }


    /**
     * 사용자 테마 설정 데이터(시스템, 라이트, 다크모드)
     */
    val appThemeLiveData: LiveData<Int> = context.dataStore.data
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
        .asLiveData()

    suspend fun saveAppTheme(code: BackgroundType) {
        Timber.i("사용자 테마 설정 저장 : $code")
        context.dataStore.edit { preference ->
            preference[appThemeKey] = code.code
        }
    }












//    /**
//     * 사용자 아이콘 타입 설정 이력(최초 한번만 요청)
//     *
//     * true: 이미 설정 -> 추가 설정 불필요
//     * false: 미설정 -> 아이콘 타입 설정 필요
//     */
//    suspend fun getIconTypeSettingHistory(): Boolean {
//        val isHistoryOfIconTypeSetting = context.dataStore.data.map { preferences ->
//            preferences[iconTypeSettingHistoryKey] ?: false
//        }.first()
//        Timber.i("아이콘 타입 설정 이력: $isHistoryOfIconTypeSetting")
//        return isHistoryOfIconTypeSetting
//    }
//
//    suspend fun saveIconTypeSettingHistory() {
//        Timber.i("아이콘 타입 설정 완료")
//        context.dataStore.edit { preference ->
//            preference[iconTypeSettingHistoryKey] = true
//        }
//    }

//    /**
//     * 사용자 설정 정렬 조건
//     */
//    suspend fun getSort(): SortType {
//        val sort = context.dataStore.data.map { preferences ->
//            preferences[sortKey] ?: SortType.NEWEST_FIRST.name
//        }.first()
//
//        val sortType = when(sort) {
//            SortType.NEWEST_FIRST.name -> SortType.NEWEST_FIRST
//            SortType.OLDEST_FIRST.name -> SortType.OLDEST_FIRST
//            SortType.ALPHABETICAL.name -> SortType.ALPHABETICAL
//            else -> {
//                Timber.e("Exception:Not handling this sort type($sort)")
//                SortType.NEWEST_FIRST
//            }
//        }
//
//        Timber.i("사용자 정렬 설정 로드 : $sortType")
//        return sortType
//    }
//
//    suspend fun saveSort(sortType: SortType) {
//        val sort = sortType.name
//
//        context.dataStore.edit { preferences ->
//            Timber.i("사용자 정렬 설정 저장 : $sort")
//            preferences[sortKey] = sort
//        }
//    }


//    /**
//     * 사용자가 실패한 인증 시도 횟수(인증 실패 횟수)
//     */
//    val authTryCountLiveData: LiveData<Int> = context.dataStore.data
//        .catch {exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            } else {
//                Timber.e("Exception(authTryCount):$exception")
//                throw exception
//            }
//        }
//        .map { preference ->
//            preference[authTryCountKey] ?: 0
//        }
//        .distinctUntilChanged()
//        .asLiveData()
//
//    suspend fun saveTryCount(tryCount: Int) {
//        context.dataStore.edit { preferences ->
//            Timber.i("인증 시도 횟수 저장 : $tryCount")
//            preferences[authTryCountKey] = tryCount
//        }
//    }

//    /**
//     * 앱 잠김 단계와 잠김 풀림 시간을 가져온다. '앱 잠김 단계|잠김 풀림 시각'
//     * ex 1|1712216907593
//     */
//    val getLockStageLiveData: LiveData<String> = context.dataStore.data
//        .catch {exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            } else {
//                Timber.e("Exception(authTryCount):$exception")
//                throw exception
//            }
//        }
//        .map { preference ->
//            preference[lockStageDataKey] ?: ""
//        }
//        .distinctUntilChanged()
//        .asLiveData()
//
//    /**
//     * 앱 잠김 단계와 잠김 풀림 시간을 저장한다. '앱 잠김 단계|잠김 풀림 시각'
//     */
//    suspend fun saveLockStageData(lockStageData: LockStageData?) {
//        if (lockStageData == null) {
//            Timber.i("LockStageData 잠금 풀림 상태 dataStore 저장")
//            context.dataStore.edit { preference ->
//                preference[lockStageDataKey] = ""
//            }
//            return
//        }
//
//        val stage = lockStageData.stage //1
//        val lockTime = lockStageData.lockTime //30000
//        val unlockTime = System.currentTimeMillis() + lockTime //1712216907593
//        val lockStage = "$stage|$unlockTime" //1|1712216907593
//
//        Timber.i("LockStageData 저장 : $lockStage")
//        context.dataStore.edit { preference ->
//            preference[lockStageDataKey] = lockStage
//        }
//    }



//
//    /**
//     * 앱 인트로 페이지 읽음
//     */
//    suspend fun getIntroPageSetting(): Boolean {
//        val readGuide = context.dataStore.data.map { preferences ->
//            preferences[introPageSettingKey] ?: false
//        }.first()
//        Timber.i("인트로 페이지 읽은 기록 : $readGuide")
//        return readGuide
//    }

//    suspend fun saveIntroPageSetting() {
//        Timber.i("인트로 페이지 읽음으로 설정 변경")
//        context.dataStore.edit { preference ->
//            preference[introPageSettingKey] = true
//        }
//    }
//
//    /**
//     * 암호화된 PIN
//     */
//    suspend fun getPIN(): String {
//        val setting = context.dataStore.data.map { preferences ->
//            preferences[pinKey] ?: ""
//        }.first()
//        Timber.i("암호화 PIN 로드 : $setting")
//        return setting
//    }
//
//    suspend fun savePIN(pin: String) {
//        Timber.i("사용자 PIN 저장(plain) : $pin")
//        context.dataStore.edit { preferences ->
//            preferences[pinKey] = EncryptionHelper.encryptByKeyStoreKey(
//                keyAlias = AppApplication.KEYSTORE_PIN_KEY_ALIAS,
//                plainText = pin
//            ) ?: ""
//        }
//    }

//    /**
//     * 앱 아이콘(리스트형, 격자형)
//     */
//    val iconTypeLiveData: LiveData<Int> = context.dataStore.data
//        .catch { exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            } else {
//                Timber.e("Exception(iconType):$exception")
//                throw exception
//            }
//        }
//        .map { preferences ->
//            preferences[iconTypeKey] ?: IconType.LIST.value //기본 값 : '리스트 타입'
//        }
//        .distinctUntilChanged()
//        .asLiveData()
//
//    suspend fun saveIconType(iconType: IconType) {
//        Timber.i("아이콘 타입 저장 : $iconType")
//        context.dataStore.edit { preferences ->
//            preferences[iconTypeKey] = iconType.value
//        }
//    }


//    /**
//     * 사용자 설정 인증 방식(PIN or PIN + Biometric)
//     */
//    val authTypeLiveData: LiveData<Int> = context.dataStore.data
//        .catch {exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            } else {
//                Timber.e("Exception(authType):$exception")
//                throw exception
//            }
//        }
//        .map { preference ->
//            preference[authTypeKey] ?: AuthType.PIN.value
//        }
//        .distinctUntilChanged()
//        .asLiveData()
//
//    /**
//     * 사용자 설정 인증 방식(PIN or PIN + Biometric)
//     */
//    suspend fun getAuthType(): Int {
//        val setting = context.dataStore.data.map { preferences ->
//            preferences[authTypeKey] ?: AuthType.PIN.value
//        }.first()
//        Timber.i("사용자 설정 인증 방식 로드 : $setting / PIN(0),BIOMETRIC(1)")
//        return setting
//    }
//
//    suspend fun saveAuthType(authType: AuthType) {
//        Timber.i("사용자 설정 인증 방식 저장 : $authType")
//        context.dataStore.edit { preference ->
//            preference[authTypeKey] = authType.value
//        }
//    }

//    /**
//     * 브라우저 연결 다이얼로그 다시 보기 설정
//     */
//    suspend fun getShowBrowserDialogAgainSetting(): Boolean {
//        val setting = context.dataStore.data.map { preferences ->
//            preferences[browserConnectDialogKey] ?: true
//        }.first()
//        Timber.i("브라우저 연결 다이얼로그 다시보기 설정 로드 : $setting")
//        return setting
//    }
//
//    suspend fun saveShowBrowserDialogAgainSetting(isShow: Boolean) {
//        Timber.i("브라우저 연결 다이얼로그 다시보기 설정 저장 : $isShow")
//        context.dataStore.edit { preferences ->
//            preferences[browserConnectDialogKey] = isShow
//        }
//    }
//
//    /**
//     * 스크린 샷 설정
//     */
//    val screenShotSetting: LiveData<Boolean> = context.dataStore.data
//        .catch { exception ->
//            if (exception is IOException) {
//                emit(emptyPreferences())
//            } else {
//                Timber.e("Exception(screenShot):$exception")
//                throw exception
//            }
//        }
//        .map { preference ->
//            preference[screenShotKey] ?: ScreenShot.BLOCK.setting //기본 값 : '스크린샷 방지'
//        }
//        .distinctUntilChanged()
//        .asLiveData()
//
//    suspend fun saveScreenShotSetting(screenshot: ScreenShot) {
//        context.dataStore.edit { preferences ->
//            preferences[screenShotKey] = screenshot.setting
//        }
//    }
//
//    /**
//     * 보안 강도 : '앱 열 때 마다' / '꼭 필요할 때만'
//     */
//    suspend fun getSecurityStrengthSetting(): Boolean {
//        val setting = context.dataStore.data.map { preferences ->
//            preferences[securityStrengthKey] ?: SecurityStrength.WHEN_NECESSARY.value //기본 값 : '꼭 필요할 때만 인증'
//        }.first()
//        Timber.i("보안 강도 설정 : $setting")
//        return setting
//    }
//
//    suspend fun saveSecurityStrength(securityStrength: SecurityStrength) = withContext(Dispatchers.IO) {
//        Timber.i("보안 강도 설정 저장 : $securityStrength")
//        context.dataStore.edit { preferences ->
//            preferences[securityStrengthKey] = securityStrength.value
//        }
//    }
}