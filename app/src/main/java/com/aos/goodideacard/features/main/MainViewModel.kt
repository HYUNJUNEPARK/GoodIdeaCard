package com.aos.goodideacard.features.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.aos.goodideacard.R
import com.aos.goodideacard.consts.AppConst
import com.aos.goodideacard.database.enitiy.CardEntityInterface
import com.aos.goodideacard.database.enitiy.EmbeddedCardEntity
import com.aos.goodideacard.database.enitiy.MergedCardDeckItem
import com.aos.goodideacard.database.enitiy.DefaultCardDeckItem
import com.aos.goodideacard.database.enitiy.UserCardDeckItem
import com.aos.goodideacard.enums.CardAction
import com.aos.goodideacard.features.base.BaseViewModel
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

    private var _goodIdeaList = MutableLiveData<List<MergedCardDeckItem>>()
    val goodIdeaList: LiveData<List<MergedCardDeckItem>> get() = _goodIdeaList

    private var _message = MutableLiveData<String?>()
    val message: LiveData<String?> get() = _message

    init {
        getData()
    }

    private fun getData() = viewModelScope.launch(Dispatchers.IO) {
        //기존에 만든 카드 덱이 있다면 그대로 사용
        val localCards = cardRepository.getAllFromCombinedCardDeck()
        if (localCards.isNotEmpty()) {
            Timber.i("local cards not empty")
            _goodIdeaList.postValue(localCards)
        }

        val defaultCardDeck = createDefaultCardList(context)
        val userCardDeck = cardRepository.getAllFromUserCardDeck()

        val mergedCardDeck = mergeCardDecks(
            defaultCardDeck = defaultCardDeck,
            userCardDeck = userCardDeck
        )


        //마지막 카드를 처음 위치로 설정
        if (cardPosition == null) cardPosition = defaultCardDeck.size - 1

        //submit
        _goodIdeaList.postValue(mergedCardDeck)
    }

    fun updateCardPosition(action: CardAction) {
        Timber.d("$action 업데이트 전 cardPosition : $cardPosition | cardSize : ${goodIdeaList.value!!.size}")

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
        if (goodIdeaList.value.isNullOrEmpty()) {
            Timber.e("Exception: ShuffleCardSet is null")
            return
        }

        val shuffleCardSet = goodIdeaList.value!!.shuffled()

        cardPosition = shuffleCardSet.size -1
        _goodIdeaList.postValue(shuffleCardSet)
    }

    /**
     * string.xml 리스트에서 기본 카드덱을 생성한다.
     *
     * @param context string resource 가져올 때 사용된다.
     */
    @SuppressLint("DiscouragedApi")
    private fun createDefaultCardList(context: Context): List<DefaultCardDeckItem> {
        /**
          val cardResources = mapOf(
               1 to Pair(R.string.idea_1_content, R.string.idea_1_whose)
               ...
          )
         */
        val cardResources = (1..AppConst.TOTAL_CARD_50).associateWith { id ->
            Pair(
                context.resources.getIdentifier("idea_${id}_content", "string", context.packageName),
                context.resources.getIdentifier("idea_${id}_whose", "string", context.packageName)
            )
        }

        val cardList = mutableListOf<DefaultCardDeckItem>()
        for (i in 1..AppConst.TOTAL_CARD_50) {
            val resources = cardResources[i]

            if (resources != null) {
                cardList.add(
                    DefaultCardDeckItem(
                        id = i.toLong(),
                        EmbeddedCardEntity(
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
     * 기본 카드덱 + 사용자 카드덱 + 다운로드 카드덱 -> 통합 카드덱
     *
     * @param defaultCardDeck 기본 카드덱 from string.xml
     * @param userCardDeck 사용자 저장 카드덱 from database
     *
     */
    private fun mergeCardDecks(
        defaultCardDeck: List<DefaultCardDeckItem>,
        userCardDeck:List<UserCardDeckItem>
    ): List<MergedCardDeckItem> {
        fun convertToMergedCardDeckItem(entity: CardEntityInterface): MergedCardDeckItem {
            return MergedCardDeckItem(
                id = entity.cardId,
                embeddedCardEntity = EmbeddedCardEntity(
                    content = entity.content,
                    whose = entity.whose
                ),
                cardType = entity.cardType
            )
        }

        Timber.d("기본 카드덱 : $defaultCardDeck")
        Timber.d("사용자 카드덱 : $userCardDeck")

        val mergedCardDeck = (defaultCardDeck + userCardDeck).let {
            it.map { convertToMergedCardDeckItem(it) }
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