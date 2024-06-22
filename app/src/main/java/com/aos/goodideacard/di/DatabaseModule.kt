package com.aos.goodideacard.di

import android.content.Context
import androidx.room.Room
import com.aos.goodideacard.database.CardPackDatabase
import com.aos.goodideacard.database.MyCardDatabase
import com.aos.goodideacard.database.dao.CardPackDao
import com.aos.goodideacard.database.dao.CardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val CARD_DATABASE = "good_idea_db_card"
    private const val CARD_PACK_DATABASE = "good_idea_db_card_pack"

    const val CARD_TABLE = "good_idea_table_card"
    const val CARD_PACK_TABLE = "good_idea_table_card_pack"

    @Provides
    @Singleton
    fun provideMyCardDatabase(@ApplicationContext context: Context): MyCardDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = MyCardDatabase::class.java,
            name = CARD_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideCardPackDatabase(@ApplicationContext context: Context): CardPackDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CardPackDatabase::class.java,
            name = CARD_PACK_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun myCardDao(myCardDatabase: MyCardDatabase): CardDao {
        return myCardDatabase.myCardDao()
    }

    @Provides
    @Singleton
    fun cardPackDao(cardPackDatabase: CardPackDatabase): CardPackDao {
        return cardPackDatabase.cardPackDao()
    }
}