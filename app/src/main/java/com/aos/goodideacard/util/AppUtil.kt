package com.aos.goodideacard.util

import android.content.Context
import android.view.Gravity
import com.aos.goodideacard.R
import com.aos.goodideacard.enums.SubContentType

object AppUtil {
    /**
     * 하단 컨텐츠가 speaker 인 경우 '- speaker -' 포맷과 Gravity.END 를 Pair 로 반환 ex. - 이소룡 -
     * 하단 컨텐츠가 description 인 경우 'description' 포맷과 Gravity.CENTER 를 Pair 로 반환
     *
     * @param bottomContent
     * @param position
     */
    fun trimWhoseData(
        context: Context,
        bottomContent: String,
        position: String
    ): Pair<Int, String?> {
        val gravity = if (position == SubContentType.DESCRIPTION.value) Gravity.CENTER else Gravity.END

        val formatString = bottomContent.takeIf { it.isNotEmpty() }?.let {
            if (position == SubContentType.DESCRIPTION.value) it
            else context.getString(R.string.format_splash_sub_content, it)
        }

        return Pair(gravity, formatString)
    }
}