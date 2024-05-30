package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.CombinedCardItem
import com.aos.goodideacard.database.enitiy.UserCardItem

interface CardRepository {
    suspend fun saveCombinedCard(card: CombinedCardItem)
    suspend fun getAllFromCombinedCardDeck(): List<CombinedCardItem>
    suspend fun clearCombinedCardDeck()

    suspend fun saveUserCard(card: UserCardItem)
    suspend fun getAllFromUserCardDeck(): List<UserCardItem>
    suspend fun clearUserCardDeck()
}