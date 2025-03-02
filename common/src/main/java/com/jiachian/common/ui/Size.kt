package com.jiachian.common.ui

import androidx.compose.runtime.Stable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Stable
data class DSSize(
    val dp2: Dp,
    val dp4: Dp,
    val dp6: Dp,
    val dp8: Dp,
    val dp12: Dp,
    val dp14: Dp,
    val dp16: Dp,
    val dp20: Dp,
    val dp36: Dp,
    val sp10: TextUnit,
    val sp12: TextUnit,
    val sp14: TextUnit,
    val sp15: TextUnit,
    val sp16: TextUnit,
    val sp18: TextUnit,
    val sp24: TextUnit,
)

internal val DSSizeDefault = DSSize(
    dp2 = 2.dp,
    dp4 = 4.dp,
    dp6 = 6.dp,
    dp8 = 8.dp,
    dp12 = 12.dp,
    dp14 = 14.dp,
    dp16 = 16.dp,
    dp20 = 20.dp,
    dp36 = 36.dp,
    sp10 = 10.sp,
    sp12 = 12.sp,
    sp14 = 14.sp,
    sp15 = 15.sp,
    sp16 = 16.sp,
    sp18 = 18.sp,
    sp24 = 24.sp,
)

val LocalSizes = staticCompositionLocalOf { DSSizeDefault }