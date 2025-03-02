package com.jiachian.cards.ui.list.model

import androidx.compose.runtime.Stable
import com.jiachian.cards.domain.model.Card

@Stable
internal data class CardListState(
    val loading: Boolean = false,
    val cards: List<Card> = emptyList(),
)