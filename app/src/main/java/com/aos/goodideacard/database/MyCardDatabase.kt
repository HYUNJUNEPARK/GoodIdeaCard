package com.aos.goodideacard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.goodideacard.database.dao.MyCardDao
import com.aos.goodideacard.database.enitiy.MyCardEntity

@Database(
    entities = [MyCardEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MyCardDatabase: RoomDatabase() {
    abstract fun myCardDao(): MyCardDao
}

