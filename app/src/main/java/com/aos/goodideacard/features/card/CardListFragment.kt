package com.aos.goodideacard.features.card

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.consts.KeyConst
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.databinding.FragmentCardListBinding
import com.aos.goodideacard.features.base.BaseFragment
import com.aos.goodideacard.features.cardpack.PopupMenu
import com.aos.goodideacard.util.JsonUtil

class CardListFragment : BaseFragment() {
    private var _binding: FragmentCardListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentCardListBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardPackString = arguments?.getString(KeyConst.CARD_PACK_BUNDLE_KEY)!!
        val cardPack = JsonUtil.deserialize<CardPackEntity>(cardPackString)

        binding.cardPackDetailTitle.text = cardPack?.name

        binding.cardPackDetailFab.setOnClickListener {
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
        Toast.makeText(requireContext(), "$action", Toast.LENGTH_SHORT).show()

        when(action) {
            PopupMenu.FabAction.ADD -> {

            }
            PopupMenu.FabAction.SHARE -> {

            }
            PopupMenu.FabAction.CANCEL -> {}
        }
    }
}