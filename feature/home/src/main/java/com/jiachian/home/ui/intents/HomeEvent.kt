package com.jiachian.home.ui.intents

import com.jiachian.home.ui.routes.HomeRoute

sealed interface HomeEvent {
    data class NavigateTo(val route: HomeRoute) : HomeEvent
}