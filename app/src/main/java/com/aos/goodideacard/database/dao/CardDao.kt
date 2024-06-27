package com.aos.goodideacard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aos.goodideacard.database.enitiy.CardEntity
import com.aos.goodideacard.di.DatabaseModule

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(card: CardEntity)

    @Transaction
    suspend fun createAndRefresh(card: CardEntity): List<CardEntity> {
        create(card)
        return getCards(card.commonCardContent.cardDeckId)
    }

    @Query("SELECT * FROM ${DatabaseModule.CARD_TABLE} WHERE cardDeckId == :deckId")
    suspend fun getCards(deckId: String): List<CardEntity>

    @Query("DELETE FROM ${DatabaseModule.CARD_TABLE}")
    suspend fun clear()
}