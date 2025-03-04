package com.jiachian.common.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Stable
data class DSFont(
    val regular10: TextStyle,
    val regular12: TextStyle,
    val regular16: TextStyle,
    val medium10: TextStyle,
    val medium14: TextStyle,
    val sfPro12: TextStyle,
    val sfPro14: TextStyle,
    val sfPro18: TextStyle,
    val semiBold14: TextStyle,
    val semiBold15: TextStyle,
    val semiBold16: TextStyle,
    val semiBold18: TextStyle,
    val bold18: TextStyle,
    val bold24: TextStyle,
)

internal val DSFontDefault = DSFont(
    regular10 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = DSSizeDefault.sp10,
    ),
    regular12 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = DSSizeDefault.sp12,
    ),
    regular16 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = DSSizeDefault.sp16,
    ),
    medium10 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = DSSizeDefault.sp10,
    ),
    medium14 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = DSSizeDefault.sp14,
    ),
    sfPro12 = TextStyle(
        fontWeight = FontWeight(510),
        fontSize = DSSizeDefault.sp12,
    ),
    sfPro14 = TextStyle(
        fontWeight = FontWeight(510),
        fontSize = DSSizeDefault.sp14,
    ),
    sfPro18 = TextStyle(
        fontWeight = FontWeight(510),
        fontSize = DSSizeDefault.sp18,
    ),
    semiBold14 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = DSSizeDefault.sp14,
    ),
    semiBold15 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = DSSizeDefault.sp15,
    ),
    semiBold16 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = DSSizeDefault.sp16,
    ),
    semiBold18 = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = DSSizeDefault.sp18,
    ),
    bold18 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = DSSizeDefault.sp18,
    ),
    bold24 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = DSSizeDefault.sp24,
    ),
)

val LocalFonts = staticCompositionLocalOf { DSFontDefault }