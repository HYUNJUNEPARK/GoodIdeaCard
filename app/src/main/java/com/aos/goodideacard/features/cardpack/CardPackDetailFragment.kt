package com.aos.goodideacard.features.cardpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aos.goodideacard.consts.KeyConst
import com.aos.goodideacard.databinding.FragmentCardPackDetailBinding
import com.aos.goodideacard.features.base.BaseFragment

class CardPackDetailFragment : BaseFragment() {
    private var _binding: FragmentCardPackDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCardPackDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardPackId = arguments?.getString(KeyConst.CARD_PACK_ID_BUNDLE_KEY)
        Toast.makeText(requireContext(), "$cardPackId", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}