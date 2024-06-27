package com.aos.goodideacard.features.carddeck

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.aos.goodideacard.R

class PopupMenu {
    enum class FabAction {
        ADD, SHARE, CANCEL
    }

    fun cardDeckDetailFabAction(
        fragment: Fragment,
        anchorView: View,
        action:(FabAction) -> Unit
    ) {
        // Inflate the popup_layout.xml
        val inflater = fragment.layoutInflater
        val popupView: View = inflater.inflate(R.layout.popup_layout_card_pack_detail, null)

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
        val wm = fragment.requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val lp = (container.layoutParams as WindowManager.LayoutParams).apply {
            flags = this.flags or WindowManager.LayoutParams.FLAG_DIM_BEHIND
            dimAmount = 0.3f
        }
        wm.updateViewLayout(container, lp)

        popupView.findViewById<ConstraintLayout>(R.id.popup_items_layout_make_card).setOnClickListener {
            action(FabAction.ADD)
            popupWindow.dismiss()
        }

        popupView.findViewById<ConstraintLayout>(R.id.popup_items_layout_share_card_pack).setOnClickListener {
            action(FabAction.SHARE)
            popupWindow.dismiss()
        }

        popupView.findViewById<ConstraintLayout>(R.id.popup_items_layout_cancel).setOnClickListener {
            action(FabAction.CANCEL)
            popupWindow.dismiss()
        }
    }
}