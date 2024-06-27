package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.CardDeckEntity
import com.aos.goodideacard.database.enitiy.CardEntity

interface CardRepository {
//    suspend fun createCard(card: CardEntity)
    suspend fun createCardAndRefresh(card: CardEntity): List<CardEntity>
    suspend fun getCards(cardDeckId: String): List<CardEntity>
    suspend fun clearMyCards()

    suspend fun createCardDeckAndRefresh(cardDeck: CardDeckEntity): List<CardDeckEntity>
    suspend fun getCardDecks(): List<CardDeckEntity>
    suspend fun clearDecks()
    suspend fun deleteCardDeckAndRefresh(cardDeck: CardDeckEntity): List<CardDeckEntity>
}