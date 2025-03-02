package com.jiachian.cards.ui.list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jiachian.cards.R
import com.jiachian.cards.ui.list.model.CardListState
import com.jiachian.common.ui.DSTheme
import com.jiachian.common.ui.NitraTheme

@Composable
internal fun CardListScreen(
    state: CardListState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
    ) {
        TopBar(
            modifier = Modifier
                .fillMaxWidth()
                .background(DSTheme.colors.background)
                .padding(horizontal = DSTheme.sizes.dp12, vertical = DSTheme.sizes.dp8),
            onHeaderClick = {},
            onTailClick = {},
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

@Preview
@Composable
fun CardListScreenPreview() {
    NitraTheme {
        CardListScreen(
            state = CardListState(),
        )
    }
}