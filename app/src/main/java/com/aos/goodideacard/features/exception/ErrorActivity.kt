package com.aos.goodideacard.features.exception

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aos.goodideacard.BuildConfig
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.ActivityErrorBinding
import com.aos.goodideacard.features.splash.SplashActivity
import com.aos.goodideacard.util.AppUtil
import com.aos.goodideacard.util.Extension.intentSerializable
import timber.log.Timber
import java.io.PrintWriter
import java.io.StringWriter

class ErrorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityErrorBinding

//    @Inject
//    lateinit var crashlytics: FirebaseCrashlytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityErrorBinding.inflate(layoutInflater)
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("ErrorActivity onCreate()")
        setContentView(binding.root)

        try {
            val throwable = intent.intentSerializable(AppExceptionHandler.EXTRA_ERROR, Throwable::class.java)

            throwable?.run {
                //crashlytics.recordException(this)

                if (BuildConfig.DEBUG) {
                    printStackTrace()

                    val stringWriter = StringWriter()
                    throwable.printStackTrace(PrintWriter(stringWriter))
                    val errorText = stringWriter.toString()
                    binding.errorTv.text = errorText
                }
            }
        } catch (e: Exception) {
            Timber.e("Exception : $e")
            //crashlytics.recordException(e)
        }

        binding.errorBtnRestart.setOnClickListener { onRestartButtonClicked() }

        binding.errorBtnInquiry.setOnClickListener { AppUtil.sendEmail(this) }
    }

    private fun onRestartButtonClicked() {
        val intent = Intent(this, SplashActivity::class.java)
        startActivity(intent)
        finish()
    }
}