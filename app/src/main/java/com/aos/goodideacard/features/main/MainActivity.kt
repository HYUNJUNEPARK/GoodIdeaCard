package com.aos.goodideacard.features.main

import android.os.Bundle
import android.view.View
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.ActivityMainBinding
import com.aos.goodideacard.enums.Language
import com.aos.goodideacard.features.base.BaseActivity
import com.aos.goodideacard.util.LocaleWrapper
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.Locale

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("MainActivity onCreate()")
        setContentView(binding.root)
    }
}