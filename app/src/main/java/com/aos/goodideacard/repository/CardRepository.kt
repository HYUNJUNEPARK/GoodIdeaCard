package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.MyCardEntity

interface CardRepository {
    suspend fun createMyCard(myCard: MyCardEntity)
    suspend fun getMyCardPack(): List<MyCardEntity>
    suspend fun clearMyCardPack()
}