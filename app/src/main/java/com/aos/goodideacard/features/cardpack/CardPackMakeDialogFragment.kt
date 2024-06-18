package com.aos.goodideacard.features.cardpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.databinding.DialogFragmentCardPackMakeBinding
import com.aos.goodideacard.features.base.BaseDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CardPackMakeDialogFragment: BaseDialogFragment() {
    private var _binding: DialogFragmentCardPackMakeBinding? = null
    private val binding get() = _binding!!

    /**
     * Bundle로 callback 파라미터를 설정하면 앱 최소화 시
     * java.lang.RuntimeException: Parcelable encountered IOException writing serializable object 발생
     */
    private var cardPackData: ((Pair<String, String?>) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onCreateView()")
        _binding = DialogFragmentCardPackMakeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.makeCardPackBtnClose.setOnClickListener { dismiss() }

        binding.makeCardPackBtnMake.setOnClickListener {
            val name = binding.makeCardPackEtName.text.toString().ifEmpty {
                Toast.makeText(requireContext(), getString(R.string.msg_input_card_pack_name), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val description = binding.makeCardPackEtDescription.text.toString().ifEmpty { null }

            returnCallback(Pair(name, description))
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setCallback(callback: (Pair<String, String?>) -> Unit) {
        cardPackData = callback
    }

    private fun returnCallback(value: Pair<String, String?>) {
        cardPackData?.invoke(value)
    }

    companion object {
        @JvmStatic
        fun newInstance() = CardPackMakeDialogFragment().apply {
            setStyle(STYLE_NO_TITLE, R.style.FullScreenDialogThemeTransparent)
        }
    }
}