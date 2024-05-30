package com.aos.goodideacard.repository

import com.aos.goodideacard.database.DefaultCardDao
import com.aos.goodideacard.database.UserCardDao
import com.aos.goodideacard.database.enitiy.CombinedCardItem
import com.aos.goodideacard.database.enitiy.UserCardItem
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val defaultCardDao: DefaultCardDao,
    private val userCardDao: UserCardDao
) : CardRepository {
    override suspend fun saveCombinedCard(card: CombinedCardItem) = defaultCardDao.save(card)
    override suspend fun getAllFromCombinedCardDeck(): List<CombinedCardItem> = defaultCardDao.getAll()
    override suspend fun clearCombinedCardDeck() = defaultCardDao.clear()
    override suspend fun saveUserCard(card: UserCardItem) = userCardDao.save(card)
    override suspend fun getAllFromUserCardDeck(): List<UserCardItem> = userCardDao.getAll()
    override suspend fun clearUserCardDeck() = userCardDao.clear()
}