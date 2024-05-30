package com.aos.goodideacard.database.enitiy

interface CardEntityInterface {
    val cardId: Long
    val cardType: Int
    val content: String
    val whose: String
}