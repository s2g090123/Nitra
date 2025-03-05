package com.jiachian.nitraassignment.ui.route

import kotlinx.serialization.Serializable

sealed interface MainRoute {
    @Serializable
    data object Home : MainRoute
}