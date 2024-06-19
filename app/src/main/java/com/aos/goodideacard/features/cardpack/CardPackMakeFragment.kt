package com.aos.goodideacard.features.cardpack

import android.app.AlertDialog
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
import com.aos.goodideacard.databinding.FragmentCardPackMakeBinding
import com.aos.goodideacard.features.base.BaseFragment
import com.aos.goodideacard.features.dialog.TwoButtonsDialog
import com.aos.goodideacard.util.JsonUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class CardPackMakeFragment : BaseFragment() {
    private var _binding: FragmentCardPackMakeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardPackViewModel by viewModels()

    private val cardPackMakeAdapter: CardPackMakeAdapter by lazy {
        CardPackMakeAdapter(
            onItemClick =  { cardPack-> //클릭된 카드팩 데이터를 직렬화한 후 Bundle에 담아 fragment에 전달
                val cardPackString = JsonUtil.serialize(cardPack)
                if (cardPackString.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.error_message), Toast.LENGTH_SHORT).show()
                    return@CardPackMakeAdapter
                }
                val cardPackBundle = bundleOf(KeyConst.CARD_PACK_BUNDLE_KEY to cardPackString)
                findNavController().navigate(R.id.action_MakeMyCardFragment_to_cardPackDetailFragment, cardPackBundle)
            },
            onItemLongClick = { cardPack ->
                cardPackItemLongClickEventHandler(cardPack)
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCardPackMakeBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.makeCardPackRecyclerView.adapter = cardPackMakeAdapter

        binding.makeMyCardFab.setOnClickListener {
            CardPackMakeDialogFragment.newInstance().apply {
                setCallback { cardPack ->
                    viewModel.createMyCardPack(
                        name = cardPack.first,
                        description = cardPack.second
                    )
                }
            }.show(requireActivity().supportFragmentManager, null)
        }

        lifecycleScope.launch {
            viewModel.cardPacks.collect { cardPacks ->
                Timber.e("cardPacks : $cardPacks")
                binding.makeMyCardEmpty.visibility = if (cardPacks.isEmpty()) View.VISIBLE else View.GONE
                cardPackMakeAdapter.submitList(cardPacks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun cardPackItemLongClickEventHandler(cardPack: CardPackEntity) {
        val menuArray = R.array.alert_dialog_card_pack
        val menuList = requireContext().resources.getStringArray(menuArray)

        val alertDialogBuilder = AlertDialog.Builder(requireContext(), R.style.MyAlertDialogStyle).apply {
            setTitle(cardPack.name)
            setItems(menuList) { /*dialogInterface*/_, idx ->
                when(idx) {
                    0 -> { //수정
                        Toast.makeText(requireContext(), "수정 ", Toast.LENGTH_SHORT).show()
//                        val bookmarkData = data.copy(isBookmarked = !isBookmarked)
//                        groupViewModel.updateGroupAndRefresh(bookmarkData)
//
//                        val msg = if (isBookmarked) R.string.msg_cancel_bookmark else R.string.msg_enroll_bookmark
//                        Toast.makeText(requireContext(), requireContext().getString(msg), Toast.LENGTH_SHORT).show()
                    }
                    1 -> {
                        TwoButtonsDialog(
                            context = requireContext(),
                            content = R.string.delete,
                            rightButtonText = R.string.delete,
                            leftButtonText = R.string.cancel,
                            rightButtonFun = { viewModel.deleteCardPack(cardPack) },
                            contentTextGravityCenter = true
                        ).show()
                    }
                    2 -> {}
                    else -> Timber.e("Not handling this idx : $idx")
                }
            }
        }
        val dialog = alertDialogBuilder.create()
        dialog.show()
    }
}