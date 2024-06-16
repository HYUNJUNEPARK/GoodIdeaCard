package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.MergedCardDeckItem
import com.aos.goodideacard.database.enitiy.UserCardPackEntity

interface CardRepository {
    suspend fun saveCombinedCard(card: MergedCardDeckItem)
    suspend fun getAllFromCombinedCardDeck(): List<MergedCardDeckItem>
    suspend fun clearCombinedCardDeck()

    suspend fun saveUserCard(card: UserCardPackEntity)
    suspend fun getAllFromUserCardDeck(): List<UserCardPackEntity>
    suspend fun clearUserCardDeck()
}