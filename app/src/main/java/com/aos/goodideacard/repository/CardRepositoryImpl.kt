package com.aos.goodideacard.repository

import com.aos.goodideacard.database.dao.CardPackDao
import com.aos.goodideacard.database.dao.MyCardDao
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.database.enitiy.MyCardEntity
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val myCardDao: MyCardDao,
    private val cardPackDao: CardPackDao
) : CardRepository {
    override suspend fun createMyCard(myCard: MyCardEntity) = myCardDao.create(myCard)
    override suspend fun getMyCards(): List<MyCardEntity> = myCardDao.getAll()
    override suspend fun clearMyCards() = myCardDao.clear()

    override suspend fun createCardPackAndRefresh(cardPack: CardPackEntity): List<CardPackEntity> = cardPackDao.createAndRefresh(cardPack)
    override suspend fun getCardPacks(): List<CardPackEntity> = cardPackDao.getAll()
    override suspend fun clearPacks() = cardPackDao.clear()
}