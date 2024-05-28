package com.aos.goodideacard.database.enitiy

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.di.DatabaseModule

/**
 * @param id
 * @param content
 * @param whose
 */
@Entity(tableName = DatabaseModule.CARD_DATABASE)
data class CardItem(
    @PrimaryKey val id: Long,
    val content: String,
    val whose: String
)
