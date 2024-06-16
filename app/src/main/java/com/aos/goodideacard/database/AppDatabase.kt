package com.aos.goodideacard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.goodideacard.database.enitiy.MergedCardDeckItem
import com.aos.goodideacard.database.enitiy.UserCardPackEntity

/**
 * 아이디/비밀번호, 웹사이트 링크를 저장하는 데이터베이스
 */
@Database(
    entities = [MergedCardDeckItem::class, UserCardPackEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun defaultCardDao(): DefaultCardDao
    abstract fun userCardDao(): MyCardDao
}

