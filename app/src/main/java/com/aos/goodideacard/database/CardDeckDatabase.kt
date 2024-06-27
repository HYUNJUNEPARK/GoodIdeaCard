package com.aos.goodideacard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.goodideacard.database.dao.CardDeckDao
import com.aos.goodideacard.database.enitiy.CardDeckEntity

@Database(
    entities = [CardDeckEntity::class],
    version = 1,
    exportSchema = false
)
abstract class CardDeckDatabase: RoomDatabase() {
    abstract fun cardDeckDao(): CardDeckDao
}

