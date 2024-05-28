package com.aos.goodideacard.features

import android.os.Bundle
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.ActivityMainBinding
import com.aos.goodideacard.features.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

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