package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.database.enitiy.MyCardEntity

interface CardRepository {
    suspend fun createMyCard(myCard: MyCardEntity)
    suspend fun getMyCards(): List<MyCardEntity>
    suspend fun clearMyCards()

    suspend fun createCardPackAndRefresh(cardPack: CardPackEntity): List<CardPackEntity>
    suspend fun getCardPacks(): List<CardPackEntity>
    suspend fun clearPacks()
}