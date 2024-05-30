package com.aos.goodideacard.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aos.goodideacard.database.enitiy.UserCardDeckItem
import com.aos.goodideacard.di.DatabaseModule

@Dao
interface UserCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: UserCardDeckItem)

    @Query("SELECT * FROM ${DatabaseModule.USER_CARD_TABLE}")
    suspend fun getAll(): List<UserCardDeckItem>

    @Query("DELETE FROM ${DatabaseModule.USER_CARD_TABLE}")
    suspend fun clear()
}