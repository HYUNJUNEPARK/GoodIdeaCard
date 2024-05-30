package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.MergedCardDeckItem
import com.aos.goodideacard.database.enitiy.UserCardDeckItem

interface CardRepository {
    suspend fun saveCombinedCard(card: MergedCardDeckItem)
    suspend fun getAllFromCombinedCardDeck(): List<MergedCardDeckItem>
    suspend fun clearCombinedCardDeck()

    suspend fun saveUserCard(card: UserCardDeckItem)
    suspend fun getAllFromUserCardDeck(): List<UserCardDeckItem>
    suspend fun clearUserCardDeck()
}