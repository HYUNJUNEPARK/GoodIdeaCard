package com.aos.goodideacard.features.cardpack

import androidx.lifecycle.viewModelScope
import com.aos.goodideacard.database.enitiy.CardPackEntity
import com.aos.goodideacard.datastore.AppDataStoreManager
import com.aos.goodideacard.features.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CardPackViewModel @Inject constructor(
    private val dataStoreManager: AppDataStoreManager
): BaseViewModel() {

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

        //TODO 로컬 DB 에 저장
    }
}