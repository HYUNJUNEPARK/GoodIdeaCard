package com.aos.goodideacard.features.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.ActivitySplashBinding
import com.aos.goodideacard.features.base.BaseActivity
import com.aos.goodideacard.features.main.MainActivity
import com.aos.goodideacard.util.Extension.colorString
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : BaseActivity() {
    private lateinit var binding: ActivitySplashBinding
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashBinding.inflate(layoutInflater)
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("Splash onCreate()")
        setContentView(binding.root)

        initSplashContent()
        initHandler()
        applyNavigationBarColor()
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

        val mainContentString = getString(resources.getIdentifier(getString(R.string.format_main_content, cardId.toString()), "string", packageName))
        splashMainContent.text = mainContentString //getString(R.string.card_1_main_content)

        val subContentString = getString(resources.getIdentifier(this@SplashActivity.getString(R.string.format_sub_content, cardId.toString()), "string", packageName))

        splashSubContent.text = subContentString //getString(R.string.card_1_sub_content)
    }

    private fun applyNavigationBarColor() {
        val colorString = (R.color.app_theme).colorString(this)
        window.navigationBarColor = Color.parseColor(colorString)
    }
}