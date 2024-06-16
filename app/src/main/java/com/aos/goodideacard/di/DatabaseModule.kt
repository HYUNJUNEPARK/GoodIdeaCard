package com.aos.goodideacard.di

import android.content.Context
import androidx.room.Room
import com.aos.goodideacard.database.AppDatabase
import com.aos.goodideacard.database.dao.MyCardDao
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

    const val MY_CARD_PACK_TABLE = "good_idea_table_my_card_pack"

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
    fun userCardDao(appDatabase: AppDatabase): MyCardDao {
        return appDatabase.userCardDao()
    }
}