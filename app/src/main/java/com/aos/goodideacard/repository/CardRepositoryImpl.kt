package com.aos.goodideacard.repository

import com.aos.goodideacard.database.dao.MyCardDao
import com.aos.goodideacard.database.enitiy.MyCardPackEntity
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val myCardDao: MyCardDao
) : CardRepository {
    override suspend fun saveMyCard(card: MyCardPackEntity) = myCardDao.save(card)
    override suspend fun getAllFromMyCardPack(): List<MyCardPackEntity> = myCardDao.getAll()
    override suspend fun clearMyCardPack() = myCardDao.clear()
}