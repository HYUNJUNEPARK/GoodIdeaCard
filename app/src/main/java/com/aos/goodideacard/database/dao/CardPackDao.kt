package com.aos.goodideacard.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.database.enitiy.MyCardEntity
import com.aos.goodideacard.di.DatabaseModule

@Dao
interface CardPackDao {
    //ABORT : The transaction is rolled back.
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun create(data: CardPackEntity)

    @Query("SELECT * FROM ${DatabaseModule.CARD_PACK_TABLE}")
    suspend fun getAll(): List<CardPackEntity>

    @Query("DELETE FROM ${DatabaseModule.CARD_PACK_TABLE}")
    suspend fun clear()

    @Delete
    suspend fun delete(data: CardPackEntity)

    @Update
    suspend fun update(data: CardPackEntity)
}