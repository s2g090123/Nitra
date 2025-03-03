package com.jiachian.cards.ui.form.event

internal sealed interface CardFormAction {
    data class ShowErrorMessage(val message: String) : CardFormAction
    data object GoToList : CardFormAction
}