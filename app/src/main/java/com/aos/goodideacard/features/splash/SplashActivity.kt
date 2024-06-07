package com.aos.goodideacard.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.ActivitySplashBinding
import com.aos.goodideacard.datastore.AppDataStoreManager
import com.aos.goodideacard.enums.Language
import com.aos.goodideacard.features.main.MainActivity
import com.aos.goodideacard.features.base.BaseActivity
import com.aos.goodideacard.util.AppUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    //TODO 깔끔하게 바꿀 것!
    companion object {
        var blocKActivityResumeAction = false
    }

    @Inject
    lateinit var appDataStoreManager: AppDataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("Splash onCreate()")
        setContentView(binding.root)

        initSplashContent()

        //TODO 시스템 언어랑 사용자 언어가 다를 때 UI가 부자연스러움 cf. 깜박이면서 글자가 바뀜
        applyLanguageSetting()

        initHandler()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) window.navigationBarColor = getColor(R.color.app_theme)
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("Splash onDestroy()")
        handler.removeCallbacks(runnable)
    }

    private fun initHandler() {
        handler = Handler(Looper.getMainLooper())
        runnable = Runnable {
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        handler.postDelayed(runnable, 1700)
    }

    /**
     * 기본 제공 카드 중 랜덤하게 하나를 뽑아 화면에 띄운다.
     */
    @SuppressLint("DiscouragedApi")
    private fun initSplashContent() = with(binding) {
        val range = (1..AppConst.TOTAL_CARD_50)
        val cardId = range.random()

        val mainContentString = getString(
            resources.getIdentifier(getString(R.string.format_main_content, cardId.toString()), "string", packageName)
        ) //getString(R.string.card_1_main_content)
        splashMainContent.text = mainContentString

        val subContentString = getString(
            resources.getIdentifier(this@SplashActivity.getString(R.string.format_sub_content, cardId.toString()), "string", packageName)
        ) //getString(R.string.card_1_sub_content)

        val subContentType = getString(
            resources.getIdentifier(this@SplashActivity.getString(R.string.format_sub_content_type, cardId.toString()), "string", packageName)
        ) //getString(R.string.card_1_sub_content_type)

        val subContent = AppUtil.trimWhoseData(
            this@SplashActivity,
            subContentString,
            subContentType
        )
        splashSubContent.text = subContent.second
    }

    private fun applyLanguageSetting() = CoroutineScope(Dispatchers.Main).launch {
        if (blocKActivityResumeAction) return@launch

        blocKActivityResumeAction = true

        val language = withContext(Dispatchers.IO) {
            appDataStoreManager.getLanguage()
        }

        if (language == Language.DEFAULT) return@launch //언어 설정이 기본 설정인 경우 사용자 언어 세팅을 할 필요가 없음

        changeLanguage(language)
    }
}