package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.database.enitiy.CardEntity

interface CardRepository {
//    suspend fun createCard(card: CardEntity)
    suspend fun createCardAndRefresh(card: CardEntity): List<CardEntity>
    suspend fun getCards(cardPackId: String): List<CardEntity>
    suspend fun clearMyCards()

    suspend fun createCardPackAndRefresh(cardPack: CardPackEntity): List<CardPackEntity>
    suspend fun getCardPacks(): List<CardPackEntity>
    suspend fun clearPacks()
    suspend fun deleteCardPackAndRefresh(cardPack: CardPackEntity): List<CardPackEntity>
}