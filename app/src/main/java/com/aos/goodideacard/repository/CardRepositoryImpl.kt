package com.aos.goodideacard.repository

import com.aos.goodideacard.database.DefaultCardDao
import com.aos.goodideacard.database.MyCardDao
import com.aos.goodideacard.database.enitiy.MergedCardDeckItem
import com.aos.goodideacard.database.enitiy.UserCardPackEntity
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val defaultCardDao: DefaultCardDao,
    private val myCardDao: MyCardDao
) : CardRepository {
    override suspend fun saveCombinedCard(card: MergedCardDeckItem) = defaultCardDao.save(card)
    override suspend fun getAllFromCombinedCardDeck(): List<MergedCardDeckItem> = defaultCardDao.getAll()
    override suspend fun clearCombinedCardDeck() = defaultCardDao.clear()
    override suspend fun saveUserCard(card: UserCardPackEntity) = myCardDao.save(card)
    override suspend fun getAllFromUserCardDeck(): List<UserCardPackEntity> = myCardDao.getAll()
    override suspend fun clearUserCardDeck() = myCardDao.clear()
}