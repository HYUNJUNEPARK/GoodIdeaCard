package com.aos.goodideacard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.goodideacard.database.dao.CardPackDao
import com.aos.goodideacard.database.dao.MyCardDao
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.database.enitiy.MyCardEntity

@Database(
    entities = [CardPackEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CardPackDatabase: RoomDatabase() {
    abstract fun cardPackDao(): CardPackDao
}

