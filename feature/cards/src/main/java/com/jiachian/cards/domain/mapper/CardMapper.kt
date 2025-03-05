package com.jiachian.cards.domain.mapper

import com.jiachian.cards.domain.model.Card
import com.jiachian.cards.ui.list.model.CardListItem
import javax.inject.Inject

internal interface CardMapper {
    fun Card.toCardListItem(): CardListItem
}

internal class CardMapperImpl @Inject constructor() : CardMapper {
    override fun Card.toCardListItem(): CardListItem {
        return CardListItem(
            id = this.id,
            cardName = this.cardName,
            cardNumberTail = this.cardNumber.takeLast(4),
        )
    }
}