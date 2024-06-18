package com.aos.goodideacard.features.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.aos.goodideacard.R

/**
 * @param title ex. 알림, 경고
 * @param content ex. 등록된 생체 정보가 없습니다. R.string.delete
 * @param rightButtonText ex. 등록하기 R.string.delete
 * @param leftButtonFun ex. 닫기 R.string.delete
 * @param contentTextGravityCenter dialogContent 정렬 상태 : true 가운데 정렬, false 기본 정렬
 */
class TwoButtonsDialog(
    context: Context,
    title: String? = null,
    content: Int,
    rightButtonText: Int,
    leftButtonText: Int,
    rightButtonFun: (() -> Unit)? = null,
    leftButtonFun: (() -> Unit)? = null,
    contentTextGravityCenter: Boolean = false
) {
    private val dialog: Dialog = Dialog(context, R.style.DialogTheme)

    init {
        @SuppressLint("InflateParams")
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_two_buttons, null).apply {
            //타이틀
            val titleVisibility = if (title.isNullOrEmpty()) View.GONE else View.VISIBLE

            findViewById<TextView>(R.id.two_button_dialog_tv_title).apply {
                visibility = titleVisibility
                text = title
            }

            findViewById<View>(R.id.two_button_dialog_tv_division_line).visibility = titleVisibility

            //메인 컨텐츠 텍스트
            findViewById<TextView>(R.id.two_button_dialog_tv_content).apply {
                gravity = if (contentTextGravityCenter) Gravity.CENTER else Gravity.NO_GRAVITY
                text = context.getString(content)
            }

            //왼쪽 버튼 텍스트
            findViewById<Button>(R.id.two_button_dialog_btn_left).apply {
                text = context.getString(leftButtonText)
                setOnClickListener {
                    leftButtonFun?.invoke()
                    dismiss()
                }
            }

            //오른쪽 버튼 텍스트
            findViewById<Button>(R.id.two_button_dialog_btn_right).apply {
                text = context.getString(rightButtonText)
                setOnClickListener {
                    rightButtonFun?.invoke()
                    dismiss()
                }
            }
        }
        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun dismiss() {
        dialog.dismiss()
    }

    fun show() {
        dialog.show()
    }
}