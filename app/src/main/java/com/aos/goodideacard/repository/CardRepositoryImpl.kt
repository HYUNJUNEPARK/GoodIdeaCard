package com.aos.goodideacard.repository

import com.aos.goodideacard.database.dao.MyCardDao
import com.aos.goodideacard.database.enitiy.MyCardEntity
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val myCardDao: MyCardDao
) : CardRepository {
    override suspend fun createMyCard(myCard: MyCardEntity) = myCardDao.create(myCard)
    override suspend fun getMyCardPack(): List<MyCardEntity> = myCardDao.getAll()
    override suspend fun clearMyCardPack() = myCardDao.clear()
}