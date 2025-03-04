package com.jiachian.cards.util

import com.jiachian.cards.domain.model.Card
import java.util.Calendar
import javax.inject.Inject

internal interface CardValidator {
    fun validateCard(card: Card): Result

    data class Result(
        var cardNameValid: Boolean = true,
        var nameOnCardValid: Boolean = true,
        var cardNumberValid: Boolean = true,
        var expYearValid: Boolean = true,
        var expMonthValid: Boolean = true,
        var cvvValid: Boolean = true,
    ) {
        val isValid: Boolean
            get() = cardNameValid && nameOnCardValid && cardNumberValid && expYearValid && expMonthValid && cvvValid
    }
}

internal class CardValidatorImpl @Inject constructor() : CardValidator {
    companion object {
        private const val VALID_CARD_NUMBER_LENGTH = 16
        private const val VALID_CVV_LENGTH = 3
    }

    override fun validateCard(card: Card): CardValidator.Result {
        val result = CardValidator.Result()

        // Validate the card name
        if (card.cardName.isBlank()) {
            result.cardNameValid = false
        }

        // Validate the name on the card
        if (card.nameOnCard.isBlank()) {
            result.nameOnCardValid = false
        }

        // Validate the card number
        if (card.cardNumber.length != VALID_CARD_NUMBER_LENGTH) {
            result.cardNumberValid = false
        }

        // Validate the expiration date
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        if (card.expYear.toInt() < currentYear + 3) {
            result.expYearValid = false
        }
        if (
            card.expMonth.toInt() !in 1..12 ||
            (card.expYear.toInt() == currentYear && card.expMonth.toInt() < currentMonth)
        ) {
            result.expMonthValid = false
        }

        // Validate the CVV
        if (card.cvv.length != VALID_CVV_LENGTH) {
            result.cvvValid = false
        }

        return result
    }
}