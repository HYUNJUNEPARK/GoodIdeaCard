package com.aos.goodideacard.model

/**
 * 카드팩 카드들의 공통 컨텐츠
 *
 * @param cardDeckId
 * @param content
 * @param subContent
 */
open class CommonCardContent (
    val cardDeckId: String,
    val content: String,
    val subContent: String
)