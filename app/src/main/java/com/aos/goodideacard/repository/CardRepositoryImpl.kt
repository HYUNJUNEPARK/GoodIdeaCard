package com.aos.goodideacard.repository

import com.aos.goodideacard.database.DefaultCardDao
import com.aos.goodideacard.database.UserCardDao
import com.aos.goodideacard.database.enitiy.MergedCardDeckItem
import com.aos.goodideacard.database.enitiy.UserCardDeckItem
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val defaultCardDao: DefaultCardDao,
    private val userCardDao: UserCardDao
) : CardRepository {
    override suspend fun saveCombinedCard(card: MergedCardDeckItem) = defaultCardDao.save(card)
    override suspend fun getAllFromCombinedCardDeck(): List<MergedCardDeckItem> = defaultCardDao.getAll()
    override suspend fun clearCombinedCardDeck() = defaultCardDao.clear()
    override suspend fun saveUserCard(card: UserCardDeckItem) = userCardDao.save(card)
    override suspend fun getAllFromUserCardDeck(): List<UserCardDeckItem> = userCardDao.getAll()
    override suspend fun clearUserCardDeck() = userCardDao.clear()
}