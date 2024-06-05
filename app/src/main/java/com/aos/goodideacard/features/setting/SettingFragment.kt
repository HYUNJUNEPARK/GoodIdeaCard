package com.aos.goodideacard.features.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
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

        initClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initClickListeners() {
        binding.makeMyCard.setOnClickListener { findNavController().navigate(R.id.action_settingFragment_to_makeMyCardFragment) }

        binding.appLanguage.setOnClickListener { findNavController().navigate(R.id.action_settingFragment_to_languageFragment) }

        binding.appBackground.setOnClickListener { findNavController().navigate(R.id.action_settingFragment_to_backgroundFragment) }
    }
}