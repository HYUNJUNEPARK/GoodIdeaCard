package com.aos.goodideacard.features.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.model.CommonCardContent
import com.aos.goodideacard.model.DefaultCardPackModel
import com.aos.goodideacard.database.enitiy.MyCardEntity
import com.aos.goodideacard.enums.CardAction
import com.aos.goodideacard.enums.CardPackType
import com.aos.goodideacard.features.base.BaseViewModel
import com.aos.goodideacard.model.CardPackInterface
import com.aos.goodideacard.model.CardPackModel
import com.aos.goodideacard.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cardRepository: CardRepository
): BaseViewModel() {
    var cardPosition: Int? = null

    private var _cardList = MutableLiveData<List<CardPackModel>>()
    val cardList: LiveData<List<CardPackModel>> get() = _cardList

    private var _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    fun getCardDeck() = viewModelScope.launch(Dispatchers.IO) {
        val defaultCardPack = createDefaultPack(context)
        val myCardPack = cardRepository.getMyCards()

        val mergedCardDeck = mergeCardPacks(
            defaultCardPack = defaultCardPack,
            myCardPack = myCardPack
        )

        //마지막 카드를 처음 위치로 설정
        if (cardPosition == null) cardPosition = defaultCardPack.size - 1

        //submit
        _cardList.postValue(mergedCardDeck)
    }

    fun updateCardPosition(action: CardAction) {
        Timber.d("$action 업데이트 전 cardPosition : $cardPosition | cardSize : ${cardList.value!!.size}")

        cardPosition = when(action) {
            CardAction.PICK -> cardPosition!!.minus(1)
            CardAction.REWIND -> cardPosition!!.plus(1)
        }
        Timber.d("$action 업데이트 후 updateCardPosition : $cardPosition")
        if (cardPosition == 0) {
            Timber.e("마지막 카드")
            _message.postValue(context.getString(R.string.msg_last_card))
        }
    }

    fun shuffleCard() {
        if (cardList.value.isNullOrEmpty()) {
            Timber.e("Exception: ShuffleCardSet is null")
            return
        }

        val shuffleCardSet = cardList.value!!.shuffled()

        cardPosition = shuffleCardSet.size -1
        _cardList.postValue(shuffleCardSet)
    }

    /**
     * string.xml 리스트에서 기본 카드팩을 생성한다.
     *
     * @param context string resource 가져올 때 사용
     */
    @SuppressLint("DiscouragedApi")
    private fun createDefaultPack(context: Context): List<DefaultCardPackModel> {
        /**
          val cardResources = mapOf(
               1 to Pair(R.string.idea_1_content, R.string.idea_1_whose)
               ...
          )
         */
        val cardResources = (1..AppConst.TOTAL_CARD_50).associateWith { cardId ->
            Pair(
                context.resources.getIdentifier(context.getString(R.string.format_main_content, cardId.toString()), "string", context.packageName),
                context.resources.getIdentifier(context.getString(R.string.format_sub_content, cardId.toString()), "string", context.packageName)
            )
        }

        val cardList = mutableListOf<DefaultCardPackModel>()
        for (i in 1..AppConst.TOTAL_CARD_50) {
            val resources = cardResources[i]

            if (resources != null) {
                cardList.add(
                    DefaultCardPackModel(
                        id = i.toLong(),
                        CommonCardContent(
                            cardPackId = CardPackType.DEFAULT.code,
                            content = context.getString(resources.first),
                            whose = context.getString(resources.second)
                        )
                    )
                )
            }
        }
        return cardList.shuffled()
    }

    /**
     * 기본 카드덱 + 사용자 카드팩 + 다운로드 카드팩 -> 통합 카드팩
     *
     * @param defaultCardPack 기본 카드덱 from string.xml
     * @param myCardPack 사용자 저장 카드덱 from database
     *
     */
    private fun mergeCardPacks(
        defaultCardPack: List<DefaultCardPackModel>,
        myCardPack: List<MyCardEntity>
    ): List<CardPackModel> {
        fun convertToModel(cardPack: CardPackInterface): CardPackModel {
            return CardPackModel(
                cardId = cardPack.cardId,
                cardPackId = cardPack.cardPackId,
                content = cardPack.content,
                whose = cardPack.whose
            )
        }

        Timber.d("기본 카드팩 : $defaultCardPack")
        Timber.d("사용자 카드팩 : $myCardPack")

        val mergedCardDeck = (defaultCardPack + myCardPack).let { interfaceList ->
            interfaceList.map { convertToModel(it) }
        }

        return mergedCardDeck
    }

    /**
     * MainFragment 에서 이동했다가 돌아오는 경우 message 가 중복해서 띄워지는 현상 블럭
     */
    fun clearMessage() {
        _message.postValue(null)
    }
}