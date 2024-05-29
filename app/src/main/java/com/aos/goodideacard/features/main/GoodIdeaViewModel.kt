package com.aos.goodideacard.features.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aos.goodideacard.features.base.BaseViewModel
import com.aos.goodideacard.database.enitiy.CardItem
import com.aos.goodideacard.enums.CardAction
import com.aos.goodideacard.util.CardUtil
import timber.log.Timber

class GoodIdeaViewModel : BaseViewModel() {

    var cardPosition: Int? = null

    private var _goodIdeaList = MutableLiveData<List<CardItem>>()
    val goodIdeaList: LiveData<List<CardItem>> get() = _goodIdeaList

    private var _message = MutableLiveData<String>()
    val message: LiveData<String> get() = _message

    fun getData(context: Context) {
        val cardSet = CardUtil.createCardList(context)

        //마지막 카드를 처음 위치로 설정
        if (cardPosition == null) cardPosition = cardSet.size - 1

        //submit
        _goodIdeaList.postValue(cardSet)
    }

    fun updateCardPosition(action: CardAction) {
        Timber.d("업데이트 전 cardPosition : $cardPosition | cardSize : ${goodIdeaList.value!!.size}")

        cardPosition = when(action) {
            CardAction.PICK -> {
                if (cardPosition == 0) {
                    Timber.i("Block update Card Position : $cardPosition")
                    _message.postValue("마지막 카드")
                    return
                }
                cardPosition!!.minus(1)
            }
            CardAction.REWIND -> {
                if (cardPosition == goodIdeaList.value!!.size) {
                    Timber.i("Block update Card Position : $cardPosition")
                    return
                }
                cardPosition!!.plus(1)
            }
        }
        Timber.d("$action 업데이트 후 updateCardPosition : $cardPosition")
    }

    fun shuffleCard() {
        if (goodIdeaList.value.isNullOrEmpty()) {
            Timber.e("Exception: ShuffleCardSet is null")
            return
        }

        val shuffleCardSet = goodIdeaList.value!!.shuffled()

        cardPosition = shuffleCardSet.size -1
        _goodIdeaList.postValue(shuffleCardSet)
    }
}