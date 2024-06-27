package com.aos.goodideacard.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.aos.goodideacard.database.enitiy.CardDeckEntity
import com.aos.goodideacard.di.DatabaseModule

@Dao
interface CardDeckDao {
    //ABORT : The transaction is rolled back.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(data: CardDeckEntity)

    @Transaction
    suspend fun createAndRefresh(data: CardDeckEntity): List<CardDeckEntity> {
        create(data)
        return getAll()
    }

    @Query("SELECT * FROM ${DatabaseModule.CARD_DECK_TABLE} ORDER BY name COLLATE NOCASE ASC")
    suspend fun getAll(): List<CardDeckEntity>

    @Query("DELETE FROM ${DatabaseModule.CARD_DECK_TABLE}")
    suspend fun clear()

    @Delete
    suspend fun delete(data: CardDeckEntity)

    suspend fun deleteAndRefresh(data: CardDeckEntity): List<CardDeckEntity> {
        delete(data)
        return getAll()
    }

    @Update
    suspend fun update(data: CardDeckEntity)


}