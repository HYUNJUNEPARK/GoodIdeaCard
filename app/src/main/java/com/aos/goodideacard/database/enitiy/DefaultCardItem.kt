package com.aos.goodideacard.database.enitiy

import androidx.room.Embedded
import androidx.room.PrimaryKey
import com.aos.goodideacard.enums.CardType

/**
 * 기본 카드덱
 */
data class DefaultCardItem(
    @PrimaryKey val id: Long,
    @Embedded val embeddedCardEntity: EmbeddedCardEntity,
) : CardEntityInterface {
    override val cardId: Long get() = id
    override val cardType get() = 0
    override val content: String get() = embeddedCardEntity.content
    override val whose: String get() = embeddedCardEntity.whose
}
