package com.aos.goodideacard.di

import com.aos.goodideacard.repository.CardRepository
import com.aos.goodideacard.repository.CardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindsCardRepository(repositoryImpl: CardRepositoryImpl): CardRepository
}