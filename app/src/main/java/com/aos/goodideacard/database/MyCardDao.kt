package com.aos.goodideacard.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aos.goodideacard.database.enitiy.UserCardPackEntity
import com.aos.goodideacard.di.DatabaseModule

@Dao
interface MyCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: UserCardPackEntity)

    @Query("SELECT * FROM ${DatabaseModule.MY_CARD_TABLE}")
    suspend fun getAll(): List<UserCardPackEntity>

    @Query("DELETE FROM ${DatabaseModule.MY_CARD_TABLE}")
    suspend fun clear()
}