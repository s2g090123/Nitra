package com.jiachian.cards.ui.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jiachian.cards.R
import com.jiachian.common.ui.DSTheme
import com.jiachian.common.ui.NitraTheme

@Composable
internal fun MaskedCreditCard(
    cardName: String,
    cardNumberTail: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .size(351.dp, 220.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(DSTheme.sizes.dp20),
        border = BorderStroke(DSTheme.sizes.dp1, DSTheme.colors.white40),
        colors = CardDefaults.cardColors(containerColor = DSTheme.colors.black),
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = DSTheme.sizes.dp16,
                    bottom = DSTheme.sizes.dp20,
                    start = DSTheme.sizes.dp16,
                    end = DSTheme.sizes.dp16,
                )
        ) {
            CreditCardTop(
                modifier = Modifier.fillMaxWidth(),
                cardName = cardName,
                cardNumberTail = cardNumberTail,
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.card_masked_card_number, cardNumberTail),
                    style = DSTheme.fonts.sfPro18.copy(color = DSTheme.colors.white),
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.ic_logo_visa),
                    contentDescription = null,
                )
            }
        }
    }
}

@Composable
private fun CreditCardTop(
    cardName: String,
    cardNumberTail: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_card_status_available),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(DSTheme.sizes.dp8))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = cardName,
                style = DSTheme.fonts.sfPro14.copy(color = DSTheme.colors.white),
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                text = stringResource(R.string.card_physical_card_number, cardNumberTail),
                style = DSTheme.fonts.regular10.copy(color = DSTheme.colors.white70),
                overflow = TextOverflow.Ellipsis,
            )
        }
        Image(
            painter = painterResource(R.drawable.ic_logo_nitra),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
private fun MaskedCreditCardPreview() {
    NitraTheme {
        MaskedCreditCard(
            cardName = "Card Name",
            cardNumberTail = "1234",
            onClick = {},
        )
    }
}