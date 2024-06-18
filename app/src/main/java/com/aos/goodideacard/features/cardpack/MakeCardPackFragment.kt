package com.aos.goodideacard.features.cardpack

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.databinding.FragmentMakeCardPackBinding
import com.aos.goodideacard.features.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MakeCardPackFragment : BaseFragment() {
    private var _binding: FragmentMakeCardPackBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CardPackViewModel by viewModels()

    private val cardPackAdapter: CardPackAdapter by lazy {
        CardPackAdapter(
            onItemClick =  { cardPack ->
                Toast.makeText(requireContext(), "Clicked", Toast.LENGTH_SHORT).show()
            },
            onItemLongClick = { cardPack ->
                Toast.makeText(requireContext(), "Long Clicked", Toast.LENGTH_SHORT).show()
            }
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMakeCardPackBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.makeCardPackRecyclerView.adapter = cardPackAdapter

        binding.makeMyCardFab.setOnClickListener {
            MakeCardPackDialogFragment.newInstance().apply {
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
                cardPackAdapter.submitList(cardPacks)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}