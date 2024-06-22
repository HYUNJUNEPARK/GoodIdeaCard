package com.aos.goodideacard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.goodideacard.database.dao.CardDao
import com.aos.goodideacard.database.enitiy.CardEntity

@Database(
    entities = [CardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyCardDatabase: RoomDatabase() {
    abstract fun myCardDao(): CardDao
}

