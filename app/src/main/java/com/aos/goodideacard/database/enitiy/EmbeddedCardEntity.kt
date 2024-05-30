package com.aos.goodideacard.database.enitiy

import androidx.room.PrimaryKey

/**
 * @param id
 * @param content
 * @param whose
 */
open class EmbeddedCardEntity (
    //@PrimaryKey(autoGenerate = true) val id: Long = 0,
    val content: String,
    val whose: String
)