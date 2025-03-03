package com.jiachian.cards.ui.form.model

import androidx.compose.runtime.Stable
import com.jiachian.cards.ui.form.event.CardFormAction

@Stable
internal data class CardFormState(
    val loading: Boolean = false,
    val cardName: String = "",
    val cardNameValid: Boolean = true,
    val nameOnCard: String = "",
    val nameOnCardValid: Boolean = true,
    val cardNumber: String = "",
    val cardNumberValid: Boolean = true,
    val expMonth: Int = 0,
    val expMonthValid: Boolean = true,
    val expYear: Int = 0,
    val expYearValid: Boolean = true,
    val cvv: String = "",
    val cvvValid: Boolean = true,
    val action: CardFormAction? = null,
)