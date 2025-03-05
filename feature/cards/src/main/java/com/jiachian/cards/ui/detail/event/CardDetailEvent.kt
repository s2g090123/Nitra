package com.jiachian.cards.ui.detail.event

internal sealed interface CardDetailEvent {
    data object ConsumeAction : CardDetailEvent
    data class UpdateCardName(val value: String) : CardDetailEvent
    data class UpdateNameOnCard(val value: String) : CardDetailEvent
    data class UpdateCardNumber(val value: String) : CardDetailEvent
    data class UpdateExpDate(val value: String) : CardDetailEvent
    data class UpdateCvv(val value: String) : CardDetailEvent
}