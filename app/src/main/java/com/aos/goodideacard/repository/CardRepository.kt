package com.aos.goodideacard.repository

import com.aos.goodideacard.database.enitiy.CardItem
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    suspend fun save(data: CardItem)
    suspend fun getAll(): List<CardItem>
    suspend fun clear()
}