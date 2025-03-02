package com.jiachian.cards.ui.route

import kotlinx.serialization.Serializable

sealed interface CardsRoute {
    @Serializable
    data object List : CardsRoute
}