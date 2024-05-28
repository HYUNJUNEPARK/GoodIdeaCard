package com.aos.goodideacard.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.database.enitiy.CardItem
import com.aos.goodideacard.di.DatabaseModule
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: CardItem)

    @Query("SELECT * FROM ${DatabaseModule.CARD_DATABASE}")
    suspend fun getAll(): List<CardItem>

    @Query("DELETE FROM ${DatabaseModule.CARD_DATABASE}")
    suspend fun clear()
}