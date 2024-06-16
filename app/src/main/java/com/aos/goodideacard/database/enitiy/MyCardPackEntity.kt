package com.aos.goodideacard.database.enitiy

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aos.goodideacard.di.DatabaseModule
import com.aos.goodideacard.enums.CardPackType
import com.aos.goodideacard.model.CardPackInterface
import com.aos.goodideacard.model.CommonCardContent

/**
 * 사용자 등록 카드팩
 */
@Entity(tableName = DatabaseModule.MY_CARD_PACK_TABLE)
data class MyCardPackEntity(
    @PrimaryKey val id: Long,
    @Embedded val commonCardContent: CommonCardContent,
) : CardPackInterface {
    override val cardId: Long get() = id
    override val cardPackId: Int get() = CardPackType.My.code
    override val content: String get() = commonCardContent.content
    override val whose: String get() = commonCardContent.whose
}
