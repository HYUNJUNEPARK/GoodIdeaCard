package com.aos.goodideacard.features.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.BuildConfig
import com.aos.goodideacard.R
import com.aos.goodideacard.databinding.FragmentSettingBinding
import com.aos.goodideacard.features.base.BaseFragment

class SettingFragment : BaseFragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setClickListeners() = with(binding) {
        if (BuildConfig.DEBUG) {
            binding.appCrash.apply {
                visibility = View.VISIBLE
                setOnClickListener { throw IllegalArgumentException("Test Crash") }
            }
        }
        makeMyCard.setOnClickListener { findNavController().navigate(R.id.action_settingFragment_to_makeMyCardFragment) }
        appLanguage.setOnClickListener { findNavController().navigate(R.id.action_settingFragment_to_languageFragment) }
        appBackground.setOnClickListener { findNavController().navigate(R.id.action_settingFragment_to_backgroundFragment) }
    }
}