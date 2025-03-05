package com.jiachian.cards.data.local.mapper

import com.jiachian.cards.data.local.model.CardEntity
import com.jiachian.cards.domain.model.Card
import com.jiachian.cards.util.Encryptor
import com.jiachian.cards.util.ExpiredDateHelper
import javax.inject.Inject

internal interface CardEntityMapper {
    fun CardEntity.toCard(): Card

    fun Card.toCardEntity(): CardEntity
}

internal class CardEntityMapperImpl @Inject constructor(
    private val encryptor: Encryptor,
    private val expiredDateHelper: ExpiredDateHelper,
) : CardEntityMapper {
    override fun CardEntity.toCard(): Card {
        return Card(
            id = this.id,
            cardName = this.cardName,
            nameOnCard = this.nameOnCard,
            cardNumber = encryptor.decrypt(this.cardNumberEncrypted),
            expYear = expiredDateHelper.yearToString(this.expYear),
            expMonth = expiredDateHelper.monthToString(this.expMonth),
            cvv = encryptor.decrypt(this.cvvEncrypted),
        )
    }

    override fun Card.toCardEntity(): CardEntity {
        return CardEntity(
            id = this.id,
            cardName = this.cardName,
            nameOnCard = this.nameOnCard,
            cardNumberEncrypted = encryptor.encrypt(this.cardNumber),
            expYear = expiredDateHelper.yearToInt(this.expYear),
            expMonth = expiredDateHelper.monthToInt(this.expMonth),
            cvvEncrypted = encryptor.encrypt(this.cvv),
        )
    }
}