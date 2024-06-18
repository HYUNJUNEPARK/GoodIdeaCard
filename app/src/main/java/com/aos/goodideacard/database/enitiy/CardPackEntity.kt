package com.aos.goodideacard.database.enitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aos.goodideacard.di.DatabaseModule

@Entity(tableName = DatabaseModule.CARD_PACK_TABLE)
data class CardPackEntity(
    @PrimaryKey val id: String,
    val name: String,
    val description: String?
)
