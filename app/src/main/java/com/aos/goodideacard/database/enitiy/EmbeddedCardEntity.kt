package com.aos.goodideacard.database.enitiy

import androidx.room.PrimaryKey

/**
 * @param id
 * @param content
 * @param whose
 */
open class EmbeddedCardEntity (
    val content: String,
    val whose: String
)