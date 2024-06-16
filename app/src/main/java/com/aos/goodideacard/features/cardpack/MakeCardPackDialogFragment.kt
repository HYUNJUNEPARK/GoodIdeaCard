package com.aos.goodideacard.features.cardpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onCreateView()")
        _binding = FragmentMakeCardPackBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.makeCardPackBtnClose.setOnClickListener { dismiss() }

        binding.makeCardPackBtnMake.setOnClickListener {
            val packName = binding.makeCardPackEtName.text.toString()
            if (packName.isEmpty()) {
                Toast.makeText(requireContext(), "카드팩 이름을 입력해주새요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val packDescription = binding.makeCardPackEtDescription.text.toString()

//            val cardPack = CardPackEntity(
//                id = System.currentTimeMillis(),
//                packName = packName,
//                description = packDescription,
//                count = 0
//            )

            //Timber.d("cardPack : $cardPack")


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = MakeCardPackDialogFragment().apply {
            setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogThemeTransparent)
        }
    }
}