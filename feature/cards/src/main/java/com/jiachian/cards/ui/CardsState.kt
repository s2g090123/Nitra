package com.jiachian.cards.ui

import androidx.compose.runtime.Stable
import com.jiachian.cards.ui.route.CardsRoute

@Stable
data class CardsState(
    val route: CardsRoute = CardsRoute.List,
)