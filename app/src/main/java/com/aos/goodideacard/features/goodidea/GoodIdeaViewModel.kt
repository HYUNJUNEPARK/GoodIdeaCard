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
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. A erat nam at lectus urna duis. Sed blandit libero volutpat sed cras ornare arcu dui. Sed adipiscing diam donec adipiscing tristique risus nec feugiat. Dui faucibus in ornare quam viverra orci sagittis eu. Duis tristique sollicitudin nibh sit amet commodo. Venenatis a condimentum vitae sapien pellentesque. Auctor neque vitae tempus quam pellentesque nec nam aliquam. Pellentesque nec nam aliquam sem et tortor consequat id. Sed euismod nisi porta lorem mollis aliquam ut.\n" +
                        "\n" +
                        "Eget aliquet nibh praesent tristique magna sit amet purus gravida. In hac habitasse platea dictumst. Accumsan sit amet nulla facilisi. Nunc eget lorem dolor sed. Aenean pharetra magna ac placerat vestibulum lectus mauris ultrices eros. Mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar. Eget nullam non nisi est sit amet facilisis magna etiam. Sit amet consectetur adipiscing elit ut. Risus feugiat in ante metus dictum at. Neque ornare aenean euismod elementum nisi quis eleifend. Pellentesque nec nam aliquam sem et. Condimentum id venenatis a condimentum.",
                whose = "Lorem Ipsum Generator Lorem Ipsum Generator"
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
                content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. A erat nam at lectus urna duis. Sed blandit libero volutpat sed cras ornare arcu dui. Sed adipiscing diam donec adipiscing tristique risus nec feugiat. Dui faucibus in ornare quam viverra orci sagittis eu. Duis tristique sollicitudin nibh sit amet commodo. Venenatis a condimentum vitae sapien pellentesque. Auctor neque vitae tempus quam pellentesque nec nam aliquam. Pellentesque nec nam aliquam sem et tortor consequat id. Sed euismod nisi porta lorem mollis aliquam ut.\n" +
                        "\n" +
                        "Eget aliquet nibh praesent tristique magna sit amet purus gravida. In hac habitasse platea dictumst. Accumsan sit amet nulla facilisi. Nunc eget lorem dolor sed. Aenean pharetra magna ac placerat vestibulum lectus mauris ultrices eros. Mauris rhoncus aenean vel elit scelerisque mauris pellentesque pulvinar. Eget nullam non nisi est sit amet facilisis magna etiam. Sit amet consectetur adipiscing elit ut. Risus feugiat in ante metus dictum at. Neque ornare aenean euismod elementum nisi quis eleifend. Pellentesque nec nam aliquam sem et. Condimentum id venenatis a condimentum.",
                whose = "Lorem Ipsum Generator Lorem Ipsum Generator"
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