package com.jiachian.cards.ui.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun <T> SwipeToDeleteContainer(
    item: T,
    onDelete: (T) -> Unit,
    modifier: Modifier = Modifier,
    animationDuration: Int = 300,
    content: @Composable (T) -> Unit
) {
    var isRemoved by remember { mutableStateOf(false) }
    val state = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.Settled) {
                false
            } else {
                isRemoved = true
                true
            }
        },
        positionalThreshold = { totalDistance -> totalDistance * 0.75f }
    )

    AnimatedVisibility(
        modifier = modifier,
        visible = !isRemoved,
        exit = slideOutHorizontally(
            animationSpec = tween(animationDuration),
            targetOffsetX = { if (state.dismissDirection == SwipeToDismissBoxValue.EndToStart) -it else it },
        )
    ) {
        SwipeToDismissBox(
            state = state,
            backgroundContent = {},
        ) {
            content(item)
        }
    }
    LaunchedEffect(isRemoved) {
        if (isRemoved) {
            delay(animationDuration.toLong())
            onDelete(item)
        }
    }
}