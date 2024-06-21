package com.aos.goodideacard.features.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.FragmentCardAddBinding
import com.aos.goodideacard.features.base.BaseDialogFragment
import timber.log.Timber

class CardAddFragment: BaseDialogFragment() {
    private var _binding: FragmentCardAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onCreateView()")
        _binding = FragmentCardAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = CardAddFragment().apply {
            setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogThemeTransparent)
        }
    }
}