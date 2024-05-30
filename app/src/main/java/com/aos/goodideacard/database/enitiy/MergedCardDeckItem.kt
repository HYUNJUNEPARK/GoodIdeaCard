package com.aos.goodideacard.database.enitiy

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aos.goodideacard.di.DatabaseModule

/**
 * 기본 카드 셋 + 사용자 등록 카드 셋 + 다운로드 카드 셋
 */
@Entity(tableName = DatabaseModule.COMBINED_CARD_TABLE)
data class MergedCardDeckItem(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val cardType: Int,
    @Embedded val embeddedCardEntity: EmbeddedCardEntity,
)
