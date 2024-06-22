package com.aos.goodideacard.model

/**
 * 카드팩 카드들의 공통 컨텐츠
 *
 * @param cardPackId
 * @param content
 * @param subContent
 */
open class CommonCardContent (
    val cardPackId: String,
    val content: String,
    val subContent: String
)