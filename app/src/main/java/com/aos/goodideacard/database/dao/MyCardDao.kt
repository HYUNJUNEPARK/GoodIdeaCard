package com.aos.goodideacard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aos.goodideacard.database.enitiy.MyCardEntity
import com.aos.goodideacard.di.DatabaseModule

@Dao
interface MyCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun create(data: MyCardEntity)

    @Query("SELECT * FROM ${DatabaseModule.MY_CARD_TABLE}")
    suspend fun getAll(): List<MyCardEntity>

    @Query("DELETE FROM ${DatabaseModule.MY_CARD_TABLE}")
    suspend fun clear()
}