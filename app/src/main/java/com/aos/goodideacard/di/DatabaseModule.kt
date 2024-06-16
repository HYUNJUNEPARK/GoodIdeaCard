package com.aos.goodideacard.di

import android.content.Context
import androidx.room.Room
import com.aos.goodideacard.database.AppDatabase
import com.aos.goodideacard.database.DefaultCardDao
import com.aos.goodideacard.database.MyCardDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val MAIN_DATABASE = "good_idea_db"

    const val COMBINED_CARD_TABLE = "good_idea_table_local_card"

    const val MY_CARD_TABLE = "good_idea_table_my_card"
    const val DOWNLOAD_CARD_PACK_TABLE = "good_idea_table_download_card_pack"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = MAIN_DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun defaultCardDao(appDatabase: AppDatabase): DefaultCardDao {
        return appDatabase.defaultCardDao()
    }

    @Provides
    @Singleton
    fun userCardDao(appDatabase: AppDatabase): MyCardDao {
        return appDatabase.userCardDao()
    }
}