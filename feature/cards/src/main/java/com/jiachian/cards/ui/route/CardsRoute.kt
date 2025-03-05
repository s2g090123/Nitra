package com.jiachian.cards.ui.route

import kotlinx.serialization.Serializable

internal sealed interface CardsRoute {
    @Serializable
    data object List : CardsRoute

    @Serializable
    data object Form : CardsRoute

    @Serializable
    data class Detail(val id: Int) : CardsRoute
}