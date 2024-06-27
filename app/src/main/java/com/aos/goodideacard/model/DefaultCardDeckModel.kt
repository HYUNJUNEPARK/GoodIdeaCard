package com.aos.goodideacard.model

/**
 * 기본 카드팩
 */
data class DefaultCardDeckModel(
    val id: Long,
    val commonCardContent: CommonCardContent,
) : CardDeckInterface {
    override val cardId: Long get() = id
    override val cardDeckId get() = commonCardContent.cardDeckId
    override val content: String get() = commonCardContent.content
    override val subContent: String get() = commonCardContent.subContent
}
