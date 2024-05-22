package com.aos.goodideacard.features

import android.os.Bundle
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.ActivityMainBinding
import com.aos.goodideacard.features.base.BaseActivity
import timber.log.Timber

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("MainActivity onCreate()")
        setContentView(binding.root)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
    }
}