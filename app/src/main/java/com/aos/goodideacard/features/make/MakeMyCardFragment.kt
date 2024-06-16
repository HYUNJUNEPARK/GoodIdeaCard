package com.aos.goodideacard.features.make

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.aos.goodideacard.R
import com.aos.goodideacard.databinding.FragmentMakeMyCardBinding
import com.aos.goodideacard.features.base.BaseFragment

class MakeMyCardFragment : BaseFragment() {
    private var _binding: FragmentMakeMyCardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentMakeMyCardBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.makeMyCardFab.setOnClickListener {
            makeCardFabAction(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun makeCardFabAction(anchorView: View) {
        // Inflate the popup_layout.xml
        val inflater = layoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout, null)

        // Create the PopupWindow
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        )

        // Set a background drawable so we can dismiss the popup by tapping outside it
        popupWindow.setBackgroundDrawable(ColorDrawable())

        popupView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
        val popupHeight = popupView.measuredHeight

        // Get the location of the anchor view on screen
        val location = IntArray(2)
        anchorView.getLocationOnScreen(location)
        val anchorX = location[0]
        val anchorY = location[1]

        // Calculate the y-offset to position the popup above the anchor view
        val yOffset =- popupHeight

        // Show the PopupWindow at the specified location
        popupWindow.showAtLocation(
            anchorView,
            Gravity.NO_GRAVITY,
            anchorX,
            anchorY + yOffset
        )

        // Dim the background when PopupWindow shows
        val container = popupWindow.contentView.rootView
        val wm = requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val lp = (container.layoutParams as WindowManager.LayoutParams).apply {
            flags = this.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.3f
        }
        wm.updateViewLayout(container, lp)

        popupView.findViewById<ConstraintLayout>(R.id.popup_items_layout_make_card_deck).setOnClickListener {
            MakeCardPackDialogFragment.newInstance().show(requireActivity().supportFragmentManager, null)
            popupWindow.dismiss()
        }
    }
}