package com.aos.goodideacard.features.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.model.CommonCardContent
import com.aos.goodideacard.model.DefaultCardDeckModel
import com.aos.goodideacard.database.enitiy.CardEntity
import com.aos.goodideacard.enums.CardAction
import com.aos.goodideacard.enums.CardDeckType
import com.aos.goodideacard.features.base.BaseViewModel
import com.aos.goodideacard.model.CardDeckInterface
import com.aos.goodideacard.model.CardDeckModel
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

    private var _cardList = MutableLiveData<List<CardDeckModel>>()
    val cardList: LiveData<List<CardDeckModel>> get() = _cardList

    private var _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    fun getCardDeck() = viewModelScope.launch(Dispatchers.IO) {
        val defaultCardDeck = createDefaultDeck(context) //strings.xml 으로 만든 기본 카드덱

        val cardDeckIds = cardRepository.getCardDecks().map { it.id }

        val userCardDecks:MutableList<List<CardEntity>> = arrayListOf()
        cardDeckIds.forEach { id ->
            Timber.e("CardDeck Id : $id")
            val myCardDeck = cardRepository.getCards(id)
            userCardDecks.add(myCardDeck)
        }

        val userCardDeck = userCardDecks.flatten()



        val mergedCardDeck = mergeCardDecks(
            defaultCardDeck = defaultCardDeck,
            myCardDeck = userCardDeck
        )

        //마지막 카드를 처음 위치로 설정
        if (cardPosition == null) cardPosition = mergedCardDeck.size - 1

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
    private fun createDefaultDeck(context: Context): List<DefaultCardDeckModel> {
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

        val cardList = mutableListOf<DefaultCardDeckModel>()
        for (i in 1..AppConst.TOTAL_CARD_50) {
            val resources = cardResources[i]

            if (resources != null) {
                cardList.add(
                    DefaultCardDeckModel(
                        id = i.toLong(),
                        CommonCardContent(
                            cardDeckId = CardDeckType.DEFAULT.name,
                            content = context.getString(resources.first),
                            subContent = context.getString(resources.second)
                        )
                    )
                )
            }
        }
        return cardList.shuffled()
    }

    /**
     * 기본 카드덱 + 사용자 카드덱 + 다운로드 카드덱 -> 통합 카드덱
     *
     * @param defaultCardDeck 기본 카드덱 from string.xml
     * @param myCardDeck 사용자 저장 카드덱 from database
     *
     */
    private fun mergeCardDecks(
        defaultCardDeck: List<DefaultCardDeckModel>,
        myCardDeck: List<CardEntity>
    ): List<CardDeckModel> {
        fun convertToModel(cardDeck: CardDeckInterface): CardDeckModel {
            return CardDeckModel(
                cardId = cardDeck.cardId,
                cardDeckId = cardDeck.cardDeckId,
                content = cardDeck.content,
                subContent = cardDeck.subContent
            )
        }

        Timber.d("기본 카드팩 : $defaultCardDeck")
        Timber.d("사용자 카드팩 : $myCardDeck")

        val mergedCardDeck = (defaultCardDeck + myCardDeck).let { interfaceList ->
            interfaceList.map { convertToModel(it) }
        }

        mergedCardDeck.forEach { it ->
            Timber.e("${it.content}")
        }

        return mergedCardDeck.shuffled()
    }

    /**
     * MainFragment 에서 이동했다가 돌아오는 경우 message 가 중복해서 띄워지는 현상 블럭
     */
    fun clearMessage() = _message.postValue(null)

}