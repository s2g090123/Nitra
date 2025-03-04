package com.jiachian.cards.ui.detail.event

internal sealed interface CardDetailAction {
    data class ShowErrorMessage(val message: String) : CardDetailAction
}