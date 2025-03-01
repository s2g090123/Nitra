package com.jiachian.cards.data.local.mapper

import com.jiachian.cards.data.local.model.CardEntity
import com.jiachian.cards.domain.model.Card

interface CardEntityMapper {
    fun CardEntity.toCard(): Card
}

internal class CardEntityMapperImpl : CardEntityMapper {
    override fun CardEntity.toCard(): Card {
        // TODO - need to implement
        return Card(
            id = this.id,
            cardName = this.cardName,
            nameOnCard = this.nameOnCard,
            cardNumber = this.cardNumberEncrypted,
            expYear = this.expDate,
            expMonth = this.expDate,
            cvv = this.cvvEncrypted,
        )
    }
}