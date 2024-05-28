package com.aos.goodideacard.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aos.goodideacard.database.enitiy.CardItem

/**
 * 아이디/비밀번호, 웹사이트 링크를 저장하는 데이터베이스
 */
@Database(
    entities = [CardItem::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cardDao(): CardDao
}

