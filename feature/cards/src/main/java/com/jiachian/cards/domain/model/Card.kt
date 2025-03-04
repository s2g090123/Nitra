package com.jiachian.cards.domain.model

internal data class Card(
    val id: Int,
    val cardName: String,
    val nameOnCard: String,
    val cardNumber: String,
    val expYear: String,
    val expMonth: String,
    val cvv: String,
)