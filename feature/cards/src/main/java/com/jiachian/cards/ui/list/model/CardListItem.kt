package com.jiachian.cards.ui.list.model

import androidx.compose.runtime.Stable

@Stable
internal data class CardListItem(
    val id: Int = 0,
    val cardName: String = "",
    val cardNumberTail: String = "0000",
)