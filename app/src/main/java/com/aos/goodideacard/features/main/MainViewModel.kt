package com.aos.goodideacard.features.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.database.enitiy.CardItem
import com.aos.goodideacard.enums.CardAction
import com.aos.goodideacard.features.base.BaseViewModel
import com.aos.goodideacard.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cardRepository: CardRepository
): BaseViewModel() {
    var cardPosition: Int? = null

    private var _goodIdeaList = MutableLiveData<List<CardItem>>()
    val goodIdeaList: LiveData<List<CardItem>> get() = _goodIdeaList

    private var _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    init {
        getData()
    }

    private fun getData() {
        val cardSet = createCardList(context)

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

    @SuppressLint("DiscouragedApi")
    private fun createCardList(context: Context): List<CardItem> {
        /**
         * val cardResources = mapOf(
         *      1 to Pair(R.string.idea_1_content, R.string.idea_1_whose)
         *      ...
         * )
         */
        val cardResources = (1..AppConst.TOTAL_CARD_20).associateWith { id ->
            Pair(
                context.resources.getIdentifier("idea_${id}_content", "string", context.packageName),
                context.resources.getIdentifier("idea_${id}_whose", "string", context.packageName)
            )
        }

        val cardList = mutableListOf<CardItem>()
        for (i in 1..AppConst.TOTAL_CARD_20) {
            val resources = cardResources[i]

            if (resources != null) {
                cardList.add(
                    CardItem(
                        id = i.toLong(),
                        content = context.getString(resources.first),
                        whose = context.getString(resources.second)
                    )
                )
            }
        }
        return cardList.shuffled()
    }

    /**
     * MainFragment 에서 이동했다가 돌아오는 경우 message 가 중복해서 띄워지는 현상 블럭
     */
    fun clearMessage() {
        _message.postValue(null)
    }
}