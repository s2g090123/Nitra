package com.jiachian.cards.ui.detail.model

import androidx.compose.runtime.Stable
import com.jiachian.cards.ui.detail.event.CardDetailAction

@Stable
internal data class CardDetailState(
    val loading: Boolean = false,
    val cardName: String = "",
    val nameOnCard: String = "",
    val cardNumber: String = "",
    val expDate: String = "",
    val cvv: String = "",
    val action: CardDetailAction? = null,
)