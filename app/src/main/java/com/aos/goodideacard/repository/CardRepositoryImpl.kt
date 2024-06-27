package com.aos.goodideacard.repository

import com.aos.goodideacard.database.dao.CardDeckDao
import com.aos.goodideacard.database.dao.CardDao
import com.aos.goodideacard.database.enitiy.CardDeckEntity
import com.aos.goodideacard.database.enitiy.CardEntity
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao,
    private val cardDeckDao: CardDeckDao
) : CardRepository {
//    override suspend fun createCard(card: CardEntity) = cardDao.create(card)
    override suspend fun createCardAndRefresh(card: CardEntity): List<CardEntity> = cardDao.createAndRefresh(card)
    override suspend fun getCards(cardDeckId: String): List<CardEntity> = cardDao.getCards(cardDeckId)
    override suspend fun clearMyCards() = cardDao.clear()

    override suspend fun createCardDeckAndRefresh(cardDeck: CardDeckEntity): List<CardDeckEntity> = cardDeckDao.createAndRefresh(cardDeck)
    override suspend fun getCardDecks(): List<CardDeckEntity> = cardDeckDao.getAll()
    override suspend fun clearDecks() = cardDeckDao.clear()
    override suspend fun deleteCardDeckAndRefresh(cardDeck: CardDeckEntity): List<CardDeckEntity> = cardDeckDao.deleteAndRefresh(cardDeck)
}