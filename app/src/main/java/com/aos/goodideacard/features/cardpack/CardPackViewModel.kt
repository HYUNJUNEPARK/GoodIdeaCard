package com.aos.goodideacard.features.cardpack

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.datastore.AppDataStoreManager
import com.aos.goodideacard.features.base.BaseViewModel
import com.aos.goodideacard.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CardPackViewModel @Inject constructor(
    private val dataStoreManager: AppDataStoreManager,
    private val cardRepository: CardRepository
): BaseViewModel() {

    init {
        getCardPacks()
    }

    private val _cardPacks = MutableStateFlow<List<CardPackEntity>>(emptyList())
    val cardPacks: StateFlow<List<CardPackEntity>> get() = _cardPacks //.asStateFlow()

    private fun getCardPacks() = viewModelScope.launch(Dispatchers.IO) {
        val cardPacks = cardRepository.getCardPacks()
        Timber.d("CardPacks : $cardPacks")
        _cardPacks.value = cardPacks
    }

    /**
     * @param name 카드팩 이름
     * @param description 카드팩 설명
     */
    fun createMyCardPack(
        name: String,
        description: String?
    ) = viewModelScope.launch(Dispatchers.IO) {
        val id = dataStoreManager.getUUID() + "_" + System.currentTimeMillis()

        val cardPack = CardPackEntity(
            id = id,
            name = name,
            description = description
        )

        Timber.d("Create CardPack : $cardPack")

        val newCardPacks = cardRepository.createCardPackAndRefresh(cardPack)
        _cardPacks.value = newCardPacks
    }
}