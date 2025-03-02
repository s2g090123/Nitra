package com.jiachian.common.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Stable
data class DSColor(
    val primary: Color,
    val primaryTeal: Color,
    val background: Color,
    val white: Color,
    val white40: Color,
    val black: Color,
    val gray0: Color,
    val gray100: Color,
    val gray200: Color,
    val gray200Light: Color,
    val gray300: Color,
    val gray400: Color,
    val gray600: Color,
    val orange400: Color,
    val green500: Color,
    val green500Overlay: Color,
    val transparent: Color,
)

internal val DSColorDefault = DSColor(
    primary = Color(0xFF264D4F),
    primaryTeal = Color(0xFF2E5E60),
    background = Color(0xFF1E3C3E),
    white = Color(0xFFFFFFFF),
    white40 = Color(0x66FFFFFF),
    black = Color(0xFF000000),
    gray0 = Color(0xFFF4F5F6),
    gray100 = Color(0xFFE3E6E8),
    gray200 = Color(0xFFD5DADC),
    gray200Light = Color(0xFFD5D5D5),
    gray300 = Color(0xFFC6CDD0),
    gray400 = Color(0xFFB8B8B8),
    gray600 = Color(0xFF73838C),
    orange400 = Color(0xFFFB7429),
    green500 = Color(0xFF15B471),
    green500Overlay = Color(0x6615B471),
    transparent = Color(0x00000000)
)

val LocalColors = staticCompositionLocalOf { DSColorDefault }