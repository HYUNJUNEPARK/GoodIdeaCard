package com.aos.goodideacard.database.enitiy

import com.aos.goodideacard.enums.CardType

/**
 * 기본 카드팩
 */
data class DefaultPackCard(
    val id: Long,
    val embeddedCardEntity: EmbeddedCardEntity,
) : CardEntityInterface {
    override val cardId: Long get() = id
    override val cardType get() = CardType.DEFAULT.code
    override val content: String get() = embeddedCardEntity.content
    override val whose: String get() = embeddedCardEntity.whose
}
