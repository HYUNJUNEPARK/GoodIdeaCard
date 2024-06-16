package com.aos.goodideacard.model

/**
 * 기본 카드팩
 */
data class DefaultCardPackModel(
    val id: Long,
    val commonCardContent: CommonCardContent,
) : CardPackInterface {
    override val cardId: Long get() = id
    override val cardPackId get() = commonCardContent.cardPackId
    override val content: String get() = commonCardContent.content
    override val whose: String get() = commonCardContent.whose
}
