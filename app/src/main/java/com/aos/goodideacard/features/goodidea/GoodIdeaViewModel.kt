package com.aos.goodideacard.features.goodidea

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aos.goodideacard.features.base.BaseViewModel
import com.aos.goodideacard.model.CardItem
import timber.log.Timber

class GoodIdeaViewModel : BaseViewModel() {
    var cardPosition: Int? = null

    enum class CardAction {
        PICK, REWIND
    }

    private var _goodIdeaList = MutableLiveData<List<CardItem>>()
    val goodIdeaList: LiveData<List<CardItem>> get() = _goodIdeaList

    init {
        getData()
    }

    private fun getData() {
        //데이터 로딩
        val cardSet = listOf(
            CardItem(
                id = 1,
                content = "Last Card",
                whose = "111"
            ),
            CardItem(
                id = 2,
                content = "BBB",
                whose = "222"
            ),
            CardItem(
                id = 3,
                content = "AAA",
                whose = "333"
            ),
            CardItem(
                id = 5,
                content = "CCC",
                whose = "333"
            ),
            CardItem(
                id = 4,
                content = "First Card",
                whose = "444"
            ),
        )

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
        val shuffleCardSet = listOf(
            CardItem(
                id = 1,
                content = "Last Card",
                whose = "111"
            ),
            CardItem(
                id = 2,
                content = "BBB",
                whose = "222"
            ),
            CardItem(
                id = 3,
                content = "AAA",
                whose = "333"
            ),
            CardItem(
                id = 5,
                content = "CCC",
                whose = "333"
            ),
            CardItem(
                id = 4,
                content = "First Card",
                whose = "444"
            )
        ).shuffled()

        cardPosition = shuffleCardSet.size
        _goodIdeaList.postValue(shuffleCardSet)
    }
}