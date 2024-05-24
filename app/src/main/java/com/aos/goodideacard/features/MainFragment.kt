package com.aos.goodideacard.features

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.R
import com.aos.goodideacard.databinding.FragmentMainBinding
import com.aos.goodideacard.features.base.BaseFragment
import com.aos.goodideacard.model.CardItem
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import timber.log.Timber

class MainFragment : BaseFragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val backPressedCallback: OnBackPressedCallback by lazy {
        doubleBackPressedCallback(requireActivity())
    }

    private val cardItemAdapter = CardItemAdapter()
    private lateinit var manager : CardStackLayoutManager


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


        manager = CardStackLayoutManager(
            requireContext(),
            object : CardStackListener {
                override fun onCardDragging(direction: Direction?, ratio: Float) {
                    Timber.d("onCardDragging $direction / $ratio")
                }
                override fun onCardSwiped(direction: Direction?) {
                    Timber.d("onCardSwiped $direction")
                }
                override fun onCardRewound() {
                    Timber.d("onCardRewound")
                }
                override fun onCardCanceled() {
                    Timber.d("onCardCanceled")
                }
                override fun onCardAppeared(view: View?, position: Int) {
                    Timber.d("onCardAppeared $view / $position")
                }
                override fun onCardDisappeared(view: View?, position: Int) {
                    Timber.d("onCardDisappeared $view / $position")
                }
            }
        )
        binding.cardStackView.adapter = cardItemAdapter
        binding.cardStackView.layoutManager = manager


        val list = listOf(
            CardItem(
                id = 1,
                content = "ABC",
                whose = "abc"
            ),
            CardItem(
                id = 2,
                content = "222",
                whose = "222"
            ),
            CardItem(
                id = 3,
                content = "333",
                whose = "333"
            ),
            CardItem(
                id = 4,
                content = "444",
                whose = "444"
            ),
        )

        cardItemAdapter.submitList(list)
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
}