package com.aos.goodideacard.features.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aos.goodideacard.databinding.FragmentBackgroundBinding
import com.aos.goodideacard.features.base.BaseFragment

class BackgroundFragment : BaseFragment() {
    private var _binding: FragmentBackgroundBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentBackgroundBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}