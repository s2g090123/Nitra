package com.jiachian.home.ui.routes

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

@Stable
sealed interface HomeRoute {
    @Serializable
    data object Transactions : HomeRoute

    @Serializable
    data object Cards : HomeRoute

    @Serializable
    data object Payments : HomeRoute

    @Serializable
    data object Statements : HomeRoute

    @Serializable
    data object More : HomeRoute
}