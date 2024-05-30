package com.aos.goodideacard.database.enitiy

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aos.goodideacard.di.DatabaseModule
import com.aos.goodideacard.enums.CardType

/**
 * 사용자 등록 카드덱
 */
@Entity(tableName = DatabaseModule.USER_CARD_TABLE)
data class UserCardDeckItem(
    @PrimaryKey val id: Long,
    @Embedded val embeddedCardEntity: EmbeddedCardEntity,
) : CardEntityInterface {
    override val cardId: Long get() = id
    override val cardType: Int get() = CardType.USER.code
    override val content: String get() = embeddedCardEntity.content
    override val whose: String get() = embeddedCardEntity.whose
}
