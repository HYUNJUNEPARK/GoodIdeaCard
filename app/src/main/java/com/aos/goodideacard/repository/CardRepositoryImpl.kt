package com.aos.goodideacard.repository

import com.aos.goodideacard.database.CardDao
import com.aos.goodideacard.database.enitiy.CardItem

class CardRepositoryImpl(
    private val cardDao: CardDao
): CardRepository {
    override suspend fun save(data: CardItem) = cardDao.save(data)
    override suspend fun getAll(): List<CardItem> = cardDao.getAll()
    override suspend fun clear() = cardDao.clear()
}