package com.jiachian.cards.ui.list.event

internal sealed interface CardListEvent {
    data class DeleteCard(val id: Int) : CardListEvent
}