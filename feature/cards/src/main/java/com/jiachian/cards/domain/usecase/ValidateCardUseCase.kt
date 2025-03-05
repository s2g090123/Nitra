package com.jiachian.cards.domain.usecase

import com.jiachian.cards.domain.model.Card
import com.jiachian.cards.util.CardValidator
import javax.inject.Inject

internal class ValidateCardUseCase @Inject constructor(
    private val cardValidator: CardValidator,
) {
    operator fun invoke(card: Card): CardValidator.Result {
        return cardValidator.validateCard(card)
    }
}