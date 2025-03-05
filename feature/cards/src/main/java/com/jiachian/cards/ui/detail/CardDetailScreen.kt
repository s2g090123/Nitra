package com.jiachian.cards.ui.detail

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jiachian.cards.R
import com.jiachian.cards.TestTag
import com.jiachian.cards.ui.card.CardTextField
import com.jiachian.cards.ui.card.DetailedCreditCard
import com.jiachian.cards.ui.detail.event.CardDetailAction
import com.jiachian.cards.ui.detail.event.CardDetailEvent
import com.jiachian.cards.ui.detail.model.CardDetailState
import com.jiachian.common.ui.DSTheme
import com.jiachian.common.ui.NitraTheme

@Composable
internal fun CardDetailScreen(
    state: CardDetailState,
    onEvent: (CardDetailEvent) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var focused by remember { mutableIntStateOf(-1) }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = DSTheme.sizes.dp16, vertical = DSTheme.sizes.dp12),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                modifier = Modifier
                    .wrapContentSize()
                    .clickable { onBackClick() }
                    .padding(end = DSTheme.sizes.dp8),
                painter = painterResource(R.drawable.ic_back),
                contentDescription = null,
                tint = DSTheme.colors.gray300,
            )
            Text(
                text = stringResource(R.string.card_detail_title),
                style = DSTheme.fonts.semiBold18.copy(color = DSTheme.colors.black),
                overflow = TextOverflow.Ellipsis,
            )
        }
        DetailedCreditCard(
            modifier = Modifier
                .padding(top = DSTheme.sizes.dp20)
                .fillMaxWidth(),
            focused = focused,
            editable = !state.loading,
            cardName = state.cardName,
            cardNumber = state.cardNumber,
            expDate = state.expDate,
            cvv = state.cvv,
            onCardNameUpdate = { onEvent(CardDetailEvent.UpdateCardName(it)) },
            onCardNumberUpdate = { onEvent(CardDetailEvent.UpdateCardNumber(it)) },
            onExpDateUpdate = { onEvent(CardDetailEvent.UpdateExpDate(it)) },
            onCvvUpdate = { onEvent(CardDetailEvent.UpdateCvv(it)) },
            onCopy = {
                clipboardManager.setText(AnnotatedString(it))
                Toast.makeText(context, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show()
            },
            onFocusChanged = { focused = it }
        )
        Column(
            modifier = Modifier
                .padding(top = DSTheme.sizes.dp20)
                .fillMaxWidth()
                .clip(RoundedCornerShape(DSTheme.sizes.dp8))
                .background(DSTheme.colors.gray0)
                .padding(DSTheme.sizes.dp12),
        ) {
            Text(
                text = stringResource(R.string.card_detail_name_on_card),
                style = DSTheme.fonts.sfPro12.copy(color = DSTheme.colors.black),
            )
            BoxWithConstraints {
                val maxWidth = maxWidth
                Row(
                    modifier = Modifier.testTag(TestTag.Detail_TextField_NameOnCard),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CardTextField(
                        modifier = Modifier.widthIn(max = maxWidth - DSTheme.sizes.dp20),
                        value = state.nameOnCard,
                        editable = !state.loading && (focused == -1 || focused == 10),
                        textStyle = DSTheme.fonts.sfPro18.copy(color = DSTheme.colors.black),
                        keyboardType = KeyboardType.Text,
                        onValueChange = { onEvent(CardDetailEvent.UpdateNameOnCard(it)) },
                        onFocusChanged = { focused = if (it) 10 else -1 },
                    )
                    Icon(
                        modifier = Modifier
                            .clickable {
                                clipboardManager.setText(AnnotatedString(state.nameOnCard))
                                Toast.makeText(
                                    context,
                                    R.string.copied_to_clipboard,
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                        painter = painterResource(R.drawable.ic_copy),
                        contentDescription = null,
                        tint = DSTheme.colors.black40,
                    )
                }
            }
        }
    }

    LaunchedEffect(state.action) {
        state.action?.let { action ->
            when (action) {
                is CardDetailAction.ShowErrorMessage -> Toast.makeText(
                    context,
                    action.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            onEvent(CardDetailEvent.ConsumeAction)
        }
    }
}

@Preview
@Composable
private fun CardDetailScreenPreview() {
    NitraTheme {
        CardDetailScreen(
            state = CardDetailState(),
            onEvent = {},
            onBackClick = {},
        )
    }
}