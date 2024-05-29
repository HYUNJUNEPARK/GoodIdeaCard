package com.aos.goodideacard.util

import android.annotation.SuppressLint
import android.content.Context
import com.aos.goodideacard.database.enitiy.CardItem

object CardUtil {
    private const val TOTAL_CARD_20 = 20

    @SuppressLint("DiscouragedApi")
    fun createCardList(context: Context): List<CardItem> {
        /**
         * val cardResources = mapOf(
         *      1 to Pair(R.string.idea_1_content, R.string.idea_1_whose)
         *      ...
         * )
         */
        val cardResources = (1..TOTAL_CARD_20).associateWith { id ->
            Pair(
                context.resources.getIdentifier("idea_${id}_content", "string", context.packageName),
                context.resources.getIdentifier("idea_${id}_whose", "string", context.packageName)
            )
        }

        val cardList = mutableListOf<CardItem>()
        for (i in 1..TOTAL_CARD_20) {
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
}