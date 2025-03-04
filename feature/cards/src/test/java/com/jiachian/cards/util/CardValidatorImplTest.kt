package com.jiachian.cards.util

import com.jiachian.cards.domain.model.Card
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

class CardValidatorImplTest {
    private val validator = CardValidatorImpl()

    @Test
    fun `validate the card with invalid cardName returns false`() {
        val card = Card(
            id = 0,
            cardName = "",
            nameOnCard = "nameOnCard",
            cardNumber = "1111222233334444",
            expYear = (Calendar.getInstance().get(Calendar.YEAR) + 3).toString(),
            expMonth = "12",
            cvv = "123",
        )
        assertFalse(validator.validateCard(card).cardNameValid)
    }

    @Test
    fun `validate the card with invalid nameOnCard returns false`() {
        val card = Card(
            id = 0,
            cardName = "cardName",
            nameOnCard = "",
            cardNumber = "1111222233334444",
            expYear = (Calendar.getInstance().get(Calendar.YEAR) + 3).toString(),
            expMonth = "12",
            cvv = "123",
        )
        assertFalse(validator.validateCard(card).nameOnCardValid)
    }

    @Test
    fun `validate the card with invalid cardNumber returns false`() {
        val card = Card(
            id = 0,
            cardName = "",
            nameOnCard = "nameOnCard",
            cardNumber = "1111",
            expYear = (Calendar.getInstance().get(Calendar.YEAR) + 3).toString(),
            expMonth = "12",
            cvv = "123",
        )
        assertFalse(validator.validateCard(card).cardNumberValid)
    }

    @Test
    fun `validate the card with invalid expYear returns false`() {
        val card = Card(
            id = 0,
            cardName = "",
            nameOnCard = "nameOnCard",
            cardNumber = "1111222233334444",
            expYear = (Calendar.getInstance().get(Calendar.YEAR)).toString(),
            expMonth = "12",
            cvv = "123",
        )
        assertFalse(validator.validateCard(card).expYearValid)
    }

    @Test
    fun `validate the card with invalid expMonth returns false`() {
        val card = Card(
            id = 0,
            cardName = "",
            nameOnCard = "nameOnCard",
            cardNumber = "1111222233334444",
            expYear = (Calendar.getInstance().get(Calendar.YEAR) + 3).toString(),
            expMonth = "13",
            cvv = "123",
        )
        assertFalse(validator.validateCard(card).expMonthValid)
    }

    @Test
    fun `validate the card with invalid cvv returns false`() {
        val card = Card(
            id = 0,
            cardName = "",
            nameOnCard = "nameOnCard",
            cardNumber = "1111222233334444",
            expYear = (Calendar.getInstance().get(Calendar.YEAR) + 3).toString(),
            expMonth = "12",
            cvv = "1234",
        )
        assertFalse(validator.validateCard(card).cvvValid)
    }

    @Test
    fun `validate the valid card returns true`() {
        val card = Card(
            id = 0,
            cardName = "cardName",
            nameOnCard = "nameOnCard",
            cardNumber = "1111222233334444",
            expYear = (Calendar.getInstance().get(Calendar.YEAR) + 3).toString(),
            expMonth = "12",
            cvv = "123",
        )
        assertTrue(validator.validateCard(card).isValid)
    }
}