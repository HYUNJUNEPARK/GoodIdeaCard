package com.aos.goodideacard.di

import android.content.Context
import androidx.room.Room
import com.aos.goodideacard.database.CardDeckDatabase
import com.aos.goodideacard.database.MyCardDatabase
import com.aos.goodideacard.database.dao.CardDeckDao
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
    private const val CARD_DECK_DATABASE = "good_idea_db_card_deck"

    const val CARD_TABLE = "good_idea_table_card"
    const val CARD_DECK_TABLE = "good_idea_table_card_deck"

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
    fun provideCardDeckDatabase(@ApplicationContext context: Context): CardDeckDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = CardDeckDatabase::class.java,
            name = CARD_DECK_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun myCardDao(myCardDatabase: MyCardDatabase): CardDao {
        return myCardDatabase.myCardDao()
    }

    @Provides
    @Singleton
    fun cardDeckDao(cardDeckDatabase: CardDeckDatabase): CardDeckDao {
        return cardDeckDatabase.cardDeckDao()
    }
}