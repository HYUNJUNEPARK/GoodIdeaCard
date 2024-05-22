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
import timber.log.Timber

class MainFragment : BaseFragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val backPressedCallback: OnBackPressedCallback by lazy {
        doubleBackPressedCallback(requireActivity())
    }

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