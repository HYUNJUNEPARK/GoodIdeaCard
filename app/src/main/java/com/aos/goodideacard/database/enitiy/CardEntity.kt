package com.aos.goodideacard.database.enitiy

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aos.goodideacard.di.DatabaseModule
import com.aos.goodideacard.enums.CardDeckType
import com.aos.goodideacard.model.CardDeckInterface
import com.aos.goodideacard.model.CommonCardContent

/**
 * 사용자 등록 카드팩
 */
@Entity(tableName = DatabaseModule.CARD_TABLE)
data class CardEntity(
    @PrimaryKey val id: Long,
    @Embedded val commonCardContent: CommonCardContent,
) : CardDeckInterface {
    override val cardId: Long get() = id
    override val cardDeckId: String get() = CardDeckType.USER.name
    override val content: String get() = commonCardContent.content
    override val subContent: String get() = commonCardContent.subContent
}
