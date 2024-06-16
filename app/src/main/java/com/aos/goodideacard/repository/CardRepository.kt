package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.MyCardPackEntity

interface CardRepository {
    suspend fun saveMyCard(card: MyCardPackEntity)
    suspend fun getAllFromMyCardPack(): List<MyCardPackEntity>
    suspend fun clearMyCardPack()
}