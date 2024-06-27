package com.aos.goodideacard.features.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.KeyConst
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.databinding.FragmentCardListBinding
import com.aos.goodideacard.features.base.BaseFragment
import com.aos.goodideacard.features.cardpack.PopupMenu
import com.aos.goodideacard.util.JsonUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CardListFragment : BaseFragment() {
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!

    private var cardPackId: String? = null

    private val viewModel: CardViewModel by viewModels()

    private val cardListAdapter: CardListAdapter by lazy {
        CardListAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCardListBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardPackString = arguments?.getString(KeyConst.CARD_PACK_BUNDLE_KEY)!!
        val cardPack = JsonUtil.deserialize<CardPackEntity>(cardPackString)

        if (cardPack == null) {
            Timber.e("Exception : CardPackEntity is Null")
            findNavController().popBackStack()
            return
        }

        binding.cardPackDetailTitle.text = getString(R.string.format_card_deck, cardPack.name)

        cardPackId = cardPack.id

        viewModel.getCards(cardPackId!!)

        binding.cardListRecyclerView.adapter = cardListAdapter


        //TODO 화면 다시 돌아오면 네번 호출됨 ....
        lifecycleScope.launch {
            viewModel.cards.collect {
                Timber.e("cardListFragment : $it")
                cardListAdapter.submitList(it)
            }
        }




        binding.cardListFab.setOnClickListener {
            PopupMenu().cardPackDetailFabAction(this, it) { action ->
                fabActionHandler(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fabActionHandler(action: PopupMenu.FabAction) {
        when(action) {
            PopupMenu.FabAction.ADD -> {
                val cardPackId = bundleOf(KeyConst.CARD_BUNDLE_KEY to cardPackId)
                findNavController().navigate(R.id.action_cardListFragment_to_cardAddFragment, cardPackId)
            }
            PopupMenu.FabAction.SHARE -> {
                Toast.makeText(requireContext(), "미구현", Toast.LENGTH_SHORT).show()
            }
            PopupMenu.FabAction.CANCEL -> {}
        }
    }
}