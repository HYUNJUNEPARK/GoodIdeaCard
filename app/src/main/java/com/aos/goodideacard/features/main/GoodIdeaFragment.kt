package com.aos.goodideacard.features.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.database.AppDatabase
import com.aos.goodideacard.databinding.FragmentGoodIdeaBinding
import com.aos.goodideacard.di.DatabaseModule
import com.aos.goodideacard.features.MainActivity
import com.aos.goodideacard.features.base.BaseFragment
import com.aos.goodideacard.repository.CardRepository
import com.aos.goodideacard.repository.CardRepositoryImpl
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class GoodIdeaFragment : BaseFragment() {
    private var _binding: FragmentGoodIdeaBinding? = null
    private val binding get() = _binding!!

    @Inject lateinit var cardRepository: CardRepository

    private val backPressedCallback: OnBackPressedCallback by lazy {
        doubleBackPressedCallback(requireActivity())
    }

    private val viewModel: GoodIdeaViewModel by viewModels()

    private val cardItemAdapter = CardItemAdapter()
    private lateinit var cardStackManager : CardStackLayoutManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this@GoodIdeaFragment, backPressedCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGoodIdeaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addToolbarIconClickedListener()

        viewModel.getData(requireContext())

        initCardStackView()
        buttonClickListener()

        viewModel.goodIdeaList.observe(viewLifecycleOwner) { goodIdeas ->
            Timber.d("cardPosition : ${viewModel.cardPosition} \n Submit CardSet : $goodIdeas")

            cardItemAdapter.submitList(goodIdeas)
            binding.cardStackView.scrollToPosition(viewModel.cardPosition ?: 0)
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), "$message", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }

    private fun buttonClickListener() {
        binding.btnRewind.setOnClickListener {
            viewModel.updateCardPosition(GoodIdeaViewModel.CardAction.REWIND)
            binding.cardStackView.rewind()
        }

        binding.btnPick.setOnClickListener {
            viewModel.updateCardPosition(GoodIdeaViewModel.CardAction.PICK)
            binding.cardStackView.pickCard()
        }

        binding.btnShuffle.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                (requireActivity() as MainActivity).showLoading()
                cardItemAdapter.submitList(null)
                delay(1000)
                viewModel.shuffleCard()
                (requireActivity() as MainActivity).hideLoading()
            }
        }
    }

    private fun addToolbarIconClickedListener() {
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when(menuItem.itemId) {
                R.id.item_setting -> {
                    findNavController().navigate(R.id.action_mainFragment_to_settingFragment)
                    true
                }
                else -> {
                    Timber.e("Exception:Unknown menuItem(${menuItem.itemId})")
                    false
                }
            }
        }
    }

    private fun initCardStackView() {
        cardStackManager = CardStackLayoutManager(
            requireContext(),
            object : CardStackListener {
                override fun onCardDragging(direction: Direction?, ratio: Float) {}
                override fun onCardSwiped(direction: Direction?) {}
                override fun onCardRewound() {}
                override fun onCardCanceled() {}
                override fun onCardAppeared(view: View?, position: Int) {}
                override fun onCardDisappeared(view: View?, position: Int) {}
            }
        ).apply {
            setStackFrom(StackFrom.Top)
            setVisibleCount(3)
            setTranslationInterval(4.0f) //4dp cf. default 8dp
        }

        binding.cardStackView.apply {
            adapter = cardItemAdapter
            layoutManager = cardStackManager
        }
    }
}