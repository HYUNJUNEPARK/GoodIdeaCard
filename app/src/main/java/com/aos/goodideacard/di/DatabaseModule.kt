package com.aos.goodideacard.di

import android.content.Context
import android.widget.Toast
import androidx.room.Room
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.database.AppDatabase
import com.aos.goodideacard.database.CardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val MAIN_DB = "good_idea_card_db"
    const val CARD_DATABASE = "good_idea_card_table_card"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = MAIN_DB
        ).build()
    }

    @Provides
    @Singleton
    fun cardDao(appDatabase: AppDatabase): CardDao {
        return appDatabase.cardDao()
    }
}