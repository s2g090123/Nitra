package com.jiachian.home.ui.states

import androidx.compose.runtime.Stable
import com.jiachian.home.ui.routes.HomeRoute

@Stable
data class HomeState(
    val route: HomeRoute = HomeRoute.Cards,
)