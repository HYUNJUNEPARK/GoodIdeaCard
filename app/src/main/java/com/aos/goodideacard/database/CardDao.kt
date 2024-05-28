package com.aos.goodideacard.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.database.enitiy.CardItem

@Dao
interface CardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: CardItem)

    @Query("SELECT * FROM ${AppConst.CARD_DATABASE}")
    suspend fun getAll(): List<CardItem>

    @Delete
    suspend fun delete(data: CardItem)

    @Query("DELETE FROM ${AppConst.CARD_DATABASE}")
    suspend fun clear()

    @Transaction
    suspend fun saveAndRefresh(data: CardItem): List<CardItem> {
        save(data)
        return getAll()
    }

    @Transaction
    suspend fun deleteAndRefresh(data: CardItem): List<CardItem> {
        delete(data)
        return getAll()
    }
}