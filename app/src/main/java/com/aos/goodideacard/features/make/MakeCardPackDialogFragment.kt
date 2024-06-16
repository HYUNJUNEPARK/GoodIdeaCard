package com.aos.goodideacard.features.make

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.FragmentMakeCardPackBinding
import com.aos.goodideacard.features.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MakeCardPackDialogFragment: BaseDialogFragment() {
    private var _binding: FragmentMakeCardPackBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            //iconType = bundle.getString(KeyConst.COMMON_BUNDLE_KEY, null)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onCreateView()")
        _binding = FragmentMakeCardPackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.makeCardDeckBtnClose.setOnClickListener { dismiss() }
        //initViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



    companion object {
        @JvmStatic
        fun newInstance() = MakeCardPackDialogFragment().apply {
            setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogThemeTransparent)

            arguments = Bundle().apply {
                //putString(KeyConst.COMMON_BUNDLE_KEY, iconType)
            }
        }
    }
}