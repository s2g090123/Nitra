package com.jiachian.cards.ui.list

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.jiachian.cards.R
import com.jiachian.cards.ui.card.MaskedCreditCard
import com.jiachian.cards.ui.list.model.CardListItem
import com.jiachian.cards.ui.list.model.CardListState
import com.jiachian.common.ui.DSTheme
import com.jiachian.common.ui.NitraTheme
import kotlinx.coroutines.delay

private const val CARDS_DISPLAY_DELAY = 300L

@Composable
internal fun CardListScreen(
    state: CardListState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(DSTheme.colors.white),
    ) {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(DSTheme.colors.background)
                .padding(horizontal = DSTheme.sizes.dp12, vertical = DSTheme.sizes.dp8),
            onHeaderClick = {},
            onTailClick = {},
        )
        Text(
            modifier = Modifier
                .padding(
                    top = DSTheme.sizes.dp20,
                    start = DSTheme.sizes.dp12,
                    end = DSTheme.sizes.dp12,
                ),
            text = stringResource(R.string.card_list_title),
            style = DSTheme.fonts.bold24.copy(color = DSTheme.colors.black),
        )
        CardDropdownMenu(
            modifier = Modifier
                .padding(
                    top = DSTheme.sizes.dp20,
                    start = DSTheme.sizes.dp12,
                    end = DSTheme.sizes.dp12,
                )
                .fillMaxWidth()
                .height(DSTheme.sizes.dp36),
            cards = state.cards,
            onItemClick = { }, // TODO - implement the callback
        )
        CardListContent(
            modifier = Modifier
                .padding(horizontal = DSTheme.sizes.dp12)
                .fillMaxSize(),
            loading = state.loading,
            cards = state.cards,
            onAddCardClick = {}, // TODO - implement the callback
            onCardClick = {}, // TODO - implement the callback
        )
    }
}

@Composable
private fun TopBar(
    onHeaderClick: () -> Unit,
    onTailClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(DSTheme.sizes.dp6))
                .background(DSTheme.colors.primary)
                .clickable { onHeaderClick() }
                .padding(horizontal = DSTheme.sizes.dp16, vertical = DSTheme.sizes.dp14),
            horizontalArrangement = Arrangement.spacedBy(DSTheme.sizes.dp16),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_hamburger),
                contentDescription = null,
                tint = DSTheme.colors.white,
            )
            Icon(
                painter = painterResource(R.drawable.ic_nitra),
                contentDescription = null,
                tint = DSTheme.colors.white,
            )
            Text(
                text = stringResource(R.string.card_list_hamburger_title),
                style = DSTheme.fonts.semiBold15.copy(DSTheme.colors.white),
            )
        }
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(DSTheme.sizes.dp6))
                .background(DSTheme.colors.primary)
                .clickable { onTailClick() }
                .padding(horizontal = DSTheme.sizes.dp16, vertical = DSTheme.sizes.dp14),
            horizontalArrangement = Arrangement.spacedBy(DSTheme.sizes.dp8),
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(DSTheme.colors.primaryTeal)
                    .padding(DSTheme.sizes.dp2),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "LC",
                    style = DSTheme.fonts.regular10.copy(color = DSTheme.colors.white),
                )
            }
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = DSTheme.colors.white,
            )
        }
    }
}

@Composable
private fun CardDropdownMenu(
    cards: List<CardListItem>,
    onItemClick: (CardListItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    var expanded by remember { mutableStateOf(false) }
    var menuWidth by remember { mutableStateOf(Dp.Unspecified) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(DSTheme.sizes.dp6))
            .background(DSTheme.colors.gray100)
            .clickable { expanded = true }
            .onSizeChanged { with(density) { menuWidth = it.width.toDp() } },
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DSTheme.sizes.dp16, vertical = DSTheme.sizes.dp8),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.card_list_my_cards),
                style = DSTheme.fonts.semiBold14.copy(color = DSTheme.colors.black),
            )
            Text(
                modifier = Modifier
                    .padding(start = DSTheme.sizes.dp8)
                    .clip(RoundedCornerShape(DSTheme.sizes.dp16))
                    .background(DSTheme.colors.gray200)
                    .padding(DSTheme.sizes.dp2),
                text = "${cards.size}",
                style = DSTheme.fonts.medium10.copy(color = DSTheme.colors.gray600),
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.size(DSTheme.sizes.dp16),
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = DSTheme.colors.black40,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            cards.forEach { card ->
                DropdownMenuItem(
                    modifier = Modifier.width(menuWidth),
                    text = {
                        Row {
                            Text(
                                text = card.cardName,
                                style = DSTheme.fonts.semiBold14.copy(color = DSTheme.colors.black),
                            )
                            Spacer(modifier = Modifier.width(DSTheme.sizes.dp8))
                            Text(
                                text = stringResource(
                                    R.string.card_masked_card_number,
                                    card.cardNumberTail
                                ),
                                style = DSTheme.fonts.semiBold14.copy(color = DSTheme.colors.black),
                            )
                        }
                    },
                    onClick = {
                        onItemClick(card)
                        expanded = false
                    }
                )
            }
        }
    }

}

@Composable
private fun CardListContent(
    loading: Boolean,
    cards: List<CardListItem>,
    onAddCardClick: () -> Unit,
    onCardClick: (CardListItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
    ) {
        when {
            loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center),
                    color = DSTheme.colors.primary,
                )
            }

            cards.isEmpty() -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Box(
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Image(
                            painter = painterResource(R.drawable.ic_no_cards),
                            contentDescription = null,
                        )
                        Text(
                            modifier = Modifier.padding(top = 160.dp),
                            text = stringResource(R.string.card_list_empty_message),
                            style = DSTheme.fonts.sfPro14.copy(
                                color = DSTheme.colors.black,
                                textAlign = TextAlign.Center,
                            )
                        )
                    }
                    Button(
                        modifier = Modifier
                            .padding(top = DSTheme.sizes.dp20)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(DSTheme.sizes.dp4),
                        colors = ButtonDefaults.buttonColors(containerColor = DSTheme.colors.orange400),
                        contentPadding = PaddingValues(DSTheme.sizes.dp14),
                        onClick = onAddCardClick,
                    ) {
                        Text(
                            text = stringResource(R.string.card_list_add_first_card),
                            style = DSTheme.fonts.semiBold16.copy(color = DSTheme.colors.white),
                        )
                    }
                }
            }

            else -> {
                var isShrinking by rememberSaveable { mutableStateOf(false) }
                val emptyHeightFraction by animateFloatAsState(
                    targetValue = if (isShrinking) 0f else 1f,
                    animationSpec = tween(durationMillis = 1000)
                )
                LaunchedEffect(Unit) {
                    delay(CARDS_DISPLAY_DELAY)
                    isShrinking = true
                }
                Column {
                    Box(modifier = Modifier.fillMaxHeight(emptyHeightFraction))
                    LazyColumn(
                        modifier = Modifier
                            .padding(top = DSTheme.sizes.dp12)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(bottom = DSTheme.sizes.dp12),
                        verticalArrangement = Arrangement.spacedBy((-168).dp),
                    ) {
                        items(cards) { card ->
                            MaskedCreditCard(
                                modifier = Modifier.fillMaxWidth(),
                                cardName = card.cardName,
                                cardNumberTail = card.cardNumberTail,
                                onClick = { onCardClick(card) },
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun CardListScreenPreview() {
    NitraTheme {
        CardListScreen(
            state = CardListState(),
        )
    }
}