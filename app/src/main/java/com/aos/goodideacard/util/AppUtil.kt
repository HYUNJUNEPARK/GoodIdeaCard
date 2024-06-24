package com.aos.goodideacard.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.aos.goodideacard.R

object AppUtil {
    /**
     * Gmail 등 사용자 기본 이메일 앱 실행
     */
    fun sendEmail(context: Context, email:Array<String>) {
        val emailSelectorIntent = Intent(Intent.ACTION_SENDTO)
        emailSelectorIntent.setData(Uri.parse("mailto:"))

        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_EMAIL, email)
            putExtra(Intent.EXTRA_TEXT, "")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            selector = emailSelectorIntent
        }

        context.startActivity(emailIntent)
    }
}