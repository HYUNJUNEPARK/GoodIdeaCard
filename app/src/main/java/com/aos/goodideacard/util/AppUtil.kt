package com.aos.goodideacard.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.Gravity
import com.aos.goodideacard.R
import com.aos.goodideacard.enums.SubContentType

object AppUtil {
    /**
     * 하단 컨텐츠가 person 인 경우 '- speaker -' 포맷과 Gravity.END 를 Pair 로 반환 ex. - 이소룡 -
     * 하단 컨텐츠가 description 인 경우 'description' 포맷과 Gravity.CENTER 를 Pair 로 반환
     *
     * @param subContent
     * @param type
     */
    fun trimWhoseData(
        context: Context,
        subContent: String,
        type: String
    ): Pair<Int, String?> {
        val gravity = if (type == SubContentType.DESCRIPTION.value) Gravity.CENTER else Gravity.END

        val formatString = subContent.takeIf { it.isNotEmpty() }?.let {
            if (type == SubContentType.DESCRIPTION.value) it
            else context.getString(R.string.format_splash_sub_content, it)
        }

        return Pair(gravity, formatString)
    }

    /**
     * Gmail 등 사용자 기본 이메일 앱 실행
     */
    fun sendEmail(context: Context) {
        val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
        emailSelectorIntent.setData(Uri.parse("mailto:"))

        val address = arrayOf(context.getString(R.string.support_email))

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, address)
            putExtra(Intent.EXTRA_TEXT, "")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            selector = emailSelectorIntent
        }

        context.startActivity(emailIntent)
    }
}