package com.jiachian.home.ui.routes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable

@Stable
internal data class TopLevelRoute<T : HomeRoute>(
    @StringRes val nameResId: Int,
    val route: T,
    @DrawableRes val iconResId: Int,
)