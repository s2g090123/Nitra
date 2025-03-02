package com.jiachian.cards.data.local.mapper

import com.jiachian.cards.data.local.model.CardEntity
import com.jiachian.cards.domain.model.Card
import com.jiachian.cards.util.Encryptor
import com.jiachian.cards.util.ExpiredDateHelper
import javax.inject.Inject

interface CardEntityMapper {
    fun CardEntity.toCard(): Card
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
            expYear = expiredDateHelper.getExpiredYear(this.expDate),
            expMonth = expiredDateHelper.getExpiredMonth(this.expDate),
            cvv = encryptor.decrypt(this.cvvEncrypted),
        )
    }
}