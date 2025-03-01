package com.jiachian.cards.domain.model

data class Card(
    val id: Int,
    val cardName: String,
    val nameOnCard: String,
    val cardNumber: String,
    val expYear: Int,
    val expMonth: Int,
    val cvv: String,
)