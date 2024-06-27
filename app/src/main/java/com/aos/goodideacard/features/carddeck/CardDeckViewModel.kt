package com.aos.goodideacard.features.carddeck

import androidx.lifecycle.viewModelScope
import com.aos.goodideacard.database.enitiy.CardDeckEntity
import com.aos.goodideacard.datastore.AppDataStoreManager
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
class CardDeckViewModel @Inject constructor(
    private val dataStoreManager: AppDataStoreManager,
    private val cardRepository: CardRepository
): BaseViewModel() {

    init {
        getCardDecks()
    }

    private val _cardDecks = MutableStateFlow<List<CardDeckEntity>>(emptyList())
    val cardDecks: StateFlow<List<CardDeckEntity>> get() = _cardDecks

    private fun getCardDecks() = viewModelScope.launch(Dispatchers.IO) {
        val cardDecks = cardRepository.getCardDecks()
        Timber.i("getCardDecks : $cardDecks")
        _cardDecks.value = cardDecks
    }

    /**
     * @param name 카드팩 이름
     * @param description 카드팩 설명
     */
    fun createCardDeck(
        name: String,
        description: String?
    ) = viewModelScope.launch(Dispatchers.IO) {
        val id = dataStoreManager.getUUID() + "_" + System.currentTimeMillis()

        val cardDeck = CardDeckEntity(
            id = id,
            name = name,
            description = description
        )
        Timber.i("createCardDeck : $cardDeck")

        val newCardDecks = cardRepository.createCardDeckAndRefresh(cardDeck)
        _cardDecks.value = newCardDecks
    }

    fun deleteCardDeck(cardDeck: CardDeckEntity) = viewModelScope.launch(Dispatchers.IO) {
        val newCardDecks = cardRepository.deleteCardDeckAndRefresh(cardDeck)
        _cardDecks.value = newCardDecks
    }
}