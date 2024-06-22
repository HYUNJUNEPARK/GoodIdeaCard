package com.aos.goodideacard.features.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.consts.KeyConst
import com.aos.goodideacard.database.enitiy.CardEntity
import com.aos.goodideacard.databinding.FragmentCardAddBinding
import com.aos.goodideacard.features.base.BaseFragment
import com.aos.goodideacard.model.CommonCardContent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CardAddFragment: BaseFragment() {
    private var _binding: FragmentCardAddBinding? = null
    private val binding get() = _binding!!

    private var cardPackId: String? = null

    private val viewModel: CardViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        Timber.tag(AppConst.LOG_TAG_LIFE_CYCLE).i("${javaClass.simpleName} onCreateView()")
        _binding = FragmentCardAddBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardPackId = arguments?.getString(KeyConst.CARD_BUNDLE_KEY)
        if (cardPackId == null) {
            Timber.e("Exception : cardPackId is null")
            findNavController().popBackStack()
            return
        }

        viewModel.getCards(cardPackId!!)

        initListener()

        lifecycleScope.launch {
            viewModel.cards.collect { cards ->
                Timber.e("cards $cards")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initListener() = with(binding) {
        cardAddFab.setOnClickListener {
            val content = cardAddContent.text.toString()
            val subContent = cardAddSubContent.text.toString()

            if (content.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.msg_input_content), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.createCardAndRefresh(
                CardEntity(
                    id = System.currentTimeMillis(),
                    commonCardContent = CommonCardContent(
                        cardPackId = cardPackId!!,
                        content = content,
                        subContent = subContent
                    )
                )
            )
            Toast.makeText(requireContext(), getString(R.string.msg_success_add_card), Toast.LENGTH_SHORT).show()

            cardAddContent.text = null
            cardAddSubContent.text = null
        }
    }
}