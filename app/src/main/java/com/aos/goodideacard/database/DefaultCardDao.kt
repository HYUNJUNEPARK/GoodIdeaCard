package com.aos.goodideacard.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.aos.goodideacard.database.enitiy.CombinedCardItem
import com.aos.goodideacard.di.DatabaseModule

@Dao
interface DefaultCardDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(data: CombinedCardItem)

    @Query("SELECT * FROM ${DatabaseModule.COMBINED_CARD_TABLE}")
    suspend fun getAll(): List<CombinedCardItem>

    @Query("DELETE FROM ${DatabaseModule.COMBINED_CARD_TABLE}")
    suspend fun clear()
}