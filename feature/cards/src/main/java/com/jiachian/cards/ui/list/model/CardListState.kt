package com.jiachian.cards.ui.list.model

import androidx.compose.runtime.Stable

@Stable
internal data class CardListState(
    val loading: Boolean = false,
    val cards: List<CardListItem> = emptyList(),
)