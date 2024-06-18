package com.aos.goodideacard.model

/**
 * 카드팩 카드들의 공통 컨텐츠
 *
 * @param cardPackId
 * @param content
 * @param whose
 */
open class CommonCardContent (
    val cardPackId: Int,
    val content: String,
    val whose: String
)