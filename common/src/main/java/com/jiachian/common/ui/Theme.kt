package com.jiachian.common.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable

object DSTheme {
    val colors: DSColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val sizes: DSSize
        @Composable
        @ReadOnlyComposable
        get() = LocalSizes.current

    val fonts: DSFont
        @Composable
        @ReadOnlyComposable
        get() = LocalFonts.current
}

@Composable
fun NitraTheme(
    colors: DSColor = DSTheme.colors,
    sizes: DSSize = DSTheme.sizes,
    fonts: DSFont = DSTheme.fonts,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColors provides colors,
        LocalSizes provides sizes,
        LocalFonts provides fonts
    ) {
        content()
    }
}