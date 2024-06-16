package com.aos.goodideacard.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aos.goodideacard.database.enitiy.MyCardPackEntity
import com.aos.goodideacard.di.DatabaseModule

@Dao
interface MyCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: MyCardPackEntity)

    @Query("SELECT * FROM ${DatabaseModule.MY_CARD_PACK_TABLE}")
    suspend fun getAll(): List<MyCardPackEntity>

    @Query("DELETE FROM ${DatabaseModule.MY_CARD_PACK_TABLE}")
    suspend fun clear()
}