package com.jiachian.cards.ui.form.event

internal sealed interface CardFormEvent {
    data object AddCard : CardFormEvent
    data object ConsumeAction : CardFormEvent
    data class UpdateCardName(val value: String) : CardFormEvent
    data class UpdateNameOnCard(val value: String) : CardFormEvent
    data class UpdateCardNumber(val value: String) : CardFormEvent
    data class UpdateExpMonth(val value: Int) : CardFormEvent
    data class UpdateExpYear(val value: Int) : CardFormEvent
    data class UpdateCvv(val value: String) : CardFormEvent
}