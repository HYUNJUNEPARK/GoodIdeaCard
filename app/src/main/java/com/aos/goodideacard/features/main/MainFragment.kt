package com.aos.goodideacard.features.main

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.R
import com.aos.goodideacard.databinding.FragmentMainBinding
import com.aos.goodideacard.enums.CardAction
import com.aos.goodideacard.features.base.BaseFragment
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.StackFrom
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class MainFragment : BaseFragment() {
    companion object {
        private const val BACK_BUTTON_DELAY_MILLIS_2000 = 2000L
        private const val CARD_BUTTON_DELAY_MILLIS_350 = 300L
    }

    private var cardButtonPressedTime = 0L
    private var backButtonPressedTime = 0L
    private val backPressedCallback: OnBackPressedCallback by lazy {
        doubleBackPressedCallback(requireActivity())
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val cardItemAdapter = CardItemAdapter()
    private lateinit var cardStackManager : CardStackLayoutManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        requireActivity().onBackPressedDispatcher.addCallback(this@MainFragment, backPressedCallback)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addToolbarIconClickedListener()

        initCardStackView()
        buttonClickListener()
        observeLiveData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
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

    /**
     * 하단 버튼을 연속해서 빠르게 누르면 생기는 UI 버그를 막기위해 버튼 press delay time 설정
     */
    private fun clickable(): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        return if (currentTimeMillis > cardButtonPressedTime + CARD_BUTTON_DELAY_MILLIS_350) {
            cardButtonPressedTime = currentTimeMillis
            true
        } else {
            false
        }
    }

    /**
     * 2초 내에 클릭을 백버튼을 두번 눌러야 앱이 종료되는 콜백
     */
    private fun doubleBackPressedCallback(activity: Activity) : OnBackPressedCallback {
        return object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentTimeMillis = System.currentTimeMillis()
                if (currentTimeMillis > backButtonPressedTime + BACK_BUTTON_DELAY_MILLIS_2000) {
                    backButtonPressedTime = currentTimeMillis
                    Toast.makeText(activity.applicationContext, activity.applicationContext.getString(R.string.msg_app_close_msg), Toast.LENGTH_SHORT).show()
                } else if (currentTimeMillis <= backButtonPressedTime + BACK_BUTTON_DELAY_MILLIS_2000) {
                    activity.finish()
                }
            }
        }
    }

    private fun buttonClickListener() {
        binding.btnRewind.setOnClickListener {
            if (!clickable()) return@setOnClickListener
            binding.cardStackView.rewind()
        }

        binding.btnPick.setOnClickListener {
            if (!clickable()) return@setOnClickListener

            if (viewModel.cardList.value.isNullOrEmpty()) {
                viewModel.getCardDeck()
            } else {
                binding.cardStackView.pickCard()
            }
        }

        binding.btnShuffle.setOnClickListener {
            if (!clickable()) return@setOnClickListener
            CoroutineScope(Dispatchers.Main).launch {
                binding.lottieLoading.playAnimation()

                cardItemAdapter.submitList(null)
                delay(1000)
                viewModel.shuffleCard()

                binding.lottieLoading.cancelAnimation()
            }
        }
    }

    private fun initCardStackView() {
        cardStackManager = CardStackLayoutManager(
            requireContext(),
            object : CardStackListener {
                override fun onCardDragging(direction: Direction?, ratio: Float) {}
                override fun onCardCanceled() {}
                override fun onCardAppeared(view: View?, position: Int) {}
                override fun onCardDisappeared(view: View?, position: Int) {}
                override fun onCardSwiped(direction: Direction?) {
                    viewModel.updateCardPosition(CardAction.REWIND)
                }
                override fun onCardRewound() {
                    viewModel.updateCardPosition(CardAction.PICK)
                }
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

    private fun observeLiveData() {
        viewModel.cardList.observe(viewLifecycleOwner) { goodIdeas ->
            Timber.d("cardPosition : ${viewModel.cardPosition} \n Submit CardSet : $goodIdeas")
            cardItemAdapter.submitList(goodIdeas)
            binding.cardStackView.scrollToPosition(viewModel.cardPosition ?: 0)
        }

        viewModel.message.observe(viewLifecycleOwner) { message ->
            if (message != null) Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            viewModel.clearMessage()
        }
    }
}