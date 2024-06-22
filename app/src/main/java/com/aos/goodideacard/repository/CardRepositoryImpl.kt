package com.aos.goodideacard.repository

import com.aos.goodideacard.database.dao.CardPackDao
import com.aos.goodideacard.database.dao.CardDao
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.database.enitiy.CardEntity
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao,
    private val cardPackDao: CardPackDao
) : CardRepository {
//    override suspend fun createCard(card: CardEntity) = cardDao.create(card)
    override suspend fun createCardAndRefresh(card: CardEntity): List<CardEntity> = cardDao.createAndRefresh(card)
    override suspend fun getCards(cardPackId: String): List<CardEntity> = cardDao.getCards(cardPackId)
    override suspend fun clearMyCards() = cardDao.clear()

    override suspend fun createCardPackAndRefresh(cardPack: CardPackEntity): List<CardPackEntity> = cardPackDao.createAndRefresh(cardPack)
    override suspend fun getCardPacks(): List<CardPackEntity> = cardPackDao.getAll()
    override suspend fun clearPacks() = cardPackDao.clear()
    override suspend fun deleteCardPackAndRefresh(cardPack: CardPackEntity): List<CardPackEntity> = cardPackDao.deleteAndRefresh(cardPack)
}