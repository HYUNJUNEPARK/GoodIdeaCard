package com.aos.goodideacard.features.card

import androidx.lifecycle.viewModelScope
import com.aos.goodideacard.database.enitiy.CardEntity
import com.aos.goodideacard.features.base.BaseViewModel
import com.aos.goodideacard.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val cardRepository: CardRepository
): BaseViewModel() {

    private val _cards = MutableStateFlow<List<CardEntity>>(emptyList())
    val cards: StateFlow<List<CardEntity>> get() = _cards

    fun getCards(cardPackId: String)  = viewModelScope.launch(Dispatchers.IO) {
        val cards = cardRepository.getCards(cardPackId)
        Timber.i("getCards : $cards")
        _cards.value = cards
    }

    fun createCardAndRefresh(card: CardEntity)  = viewModelScope.launch(Dispatchers.IO) {
        Timber.i("createCard : $card / packId : ${card.commonCardContent.cardPackId}")
        val newCards = cardRepository.createCardAndRefresh(card)
        _cards.value = newCards
    }
}