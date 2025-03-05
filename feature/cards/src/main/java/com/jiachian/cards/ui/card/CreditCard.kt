package com.jiachian.cards.ui.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jiachian.cards.R
import com.jiachian.cards.TestTag
import com.jiachian.cards.ui.detail.transformation.ExpDateTransformation
import com.jiachian.cards.ui.form.transformation.CardNumberTransformation
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
            .aspectRatio(1.6f)
            .clickable { onClick() },
        shape = RoundedCornerShape(DSTheme.sizes.dp20),
        border = BorderStroke(DSTheme.sizes.dp1, DSTheme.colors.white40),
        colors = CardDefaults.cardColors(containerColor = DSTheme.colors.black),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
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
internal fun DetailedCreditCard(
    focused: Int,
    editable: Boolean,
    cardName: String,
    cardNumber: String,
    expDate: String,
    cvv: String,
    onCardNameUpdate: (String) -> Unit,
    onCardNumberUpdate: (String) -> Unit,
    onExpDateUpdate: (String) -> Unit,
    onCvvUpdate: (String) -> Unit,
    onCopy: (String) -> Unit,
    onFocusChanged: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.aspectRatio(1.6f),
        shape = RoundedCornerShape(DSTheme.sizes.dp20),
        border = BorderStroke(DSTheme.sizes.dp1, DSTheme.colors.white40),
        colors = CardDefaults.cardColors(containerColor = DSTheme.colors.black),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = DSTheme.sizes.dp16,
                    bottom = DSTheme.sizes.dp20,
                    start = DSTheme.sizes.dp16,
                    end = DSTheme.sizes.dp16,
                )
        ) {
            Column {
                CreditCardTop(
                    modifier = Modifier
                        .testTag(TestTag.DetailedCreditCard_CardName)
                        .fillMaxWidth(),
                    editable = editable && (focused == -1 || focused == 0),
                    cardName = cardName,
                    cardNumberTail = cardNumber.takeLast(4),
                    onCardNameUpdate = onCardNameUpdate,
                    onFocusChanged = { onFocusChanged(if (it) 0 else -1) },
                )
                Text(
                    modifier = Modifier.padding(top = DSTheme.sizes.dp40),
                    text = stringResource(R.string.card_card_number),
                    style = DSTheme.fonts.sfPro12.copy(color = DSTheme.colors.white),
                )
                Row(
                    modifier = Modifier
                        .testTag(TestTag.DetailedCreditCard_CardNumber)
                        .padding(top = DSTheme.sizes.dp8),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CardTextField(
                        value = cardNumber.chunked(4).joinToString(" "),
                        editable = editable && (focused == -1 || focused == 1),
                        maxLength = 16,
                        textStyle = DSTheme.fonts.sfPro18.copy(color = DSTheme.colors.white),
                        keyboardType = KeyboardType.Number,
                        onValueChange = onCardNumberUpdate,
                        visualTransformation = CardNumberTransformation(),
                        onFocusChanged = { onFocusChanged(if (it) 1 else -1) },
                    )
                    Icon(
                        modifier = Modifier.clickable { onCopy(cardNumber) },
                        painter = painterResource(R.drawable.ic_copy),
                        contentDescription = null,
                        tint = DSTheme.colors.white,
                    )
                }
                Row(
                    modifier = Modifier.padding(top = DSTheme.sizes.dp20)
                ) {
                    Column {
                        Text(
                            text = stringResource(R.string.card_exp_date),
                            style = DSTheme.fonts.sfPro12.copy(color = DSTheme.colors.white),
                        )
                        Row(
                            modifier = Modifier
                                .testTag(TestTag.DetailedCreditCard_ExpDate)
                                .padding(top = DSTheme.sizes.dp8),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CardTextField(
                                value = expDate.chunked(2).joinToString("/"),
                                editable = editable && (focused == -1 || focused == 2),
                                maxLength = 4,
                                textStyle = DSTheme.fonts.sfPro18.copy(color = DSTheme.colors.white),
                                keyboardType = KeyboardType.Number,
                                onValueChange = { onExpDateUpdate(it) },
                                visualTransformation = ExpDateTransformation(),
                                onFocusChanged = { onFocusChanged(if (it) 2 else -1) },
                            )
                            Icon(
                                modifier = Modifier.clickable { onCopy(expDate) },
                                painter = painterResource(R.drawable.ic_copy),
                                contentDescription = null,
                                tint = DSTheme.colors.white,
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.padding(start = DSTheme.sizes.dp56),
                    ) {
                        Text(
                            text = stringResource(R.string.card_cvv),
                            style = DSTheme.fonts.sfPro12.copy(color = DSTheme.colors.white),
                        )
                        Row(
                            modifier = Modifier
                                .testTag(TestTag.DetailedCreditCard_CVV)
                                .padding(top = DSTheme.sizes.dp8),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            CardTextField(
                                value = cvv,
                                editable = editable && (focused == -1 || focused == 3),
                                maxLength = 3,
                                textStyle = DSTheme.fonts.sfPro18.copy(color = DSTheme.colors.white),
                                keyboardType = KeyboardType.Number,
                                onValueChange = { onCvvUpdate(it) },
                                onFocusChanged = { onFocusChanged(if (it) 3 else -1) },
                            )
                            Icon(
                                modifier = Modifier.clickable { onCopy(cvv) },
                                painter = painterResource(R.drawable.ic_copy),
                                contentDescription = null,
                                tint = DSTheme.colors.white,
                            )
                        }
                    }
                }
            }
            Image(
                modifier = Modifier.align(Alignment.BottomEnd),
                painter = painterResource(R.drawable.ic_logo_visa),
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun CreditCardTop(
    cardName: String,
    cardNumberTail: String,
    modifier: Modifier = Modifier,
    editable: Boolean = false,
    onCardNameUpdate: (String) -> Unit = {},
    onFocusChanged: (Boolean) -> Unit = {},
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
            CardTextField(
                modifier = Modifier.fillMaxWidth(),
                value = cardName,
                editable = editable,
                textStyle = DSTheme.fonts.sfPro14.copy(color = DSTheme.colors.white),
                keyboardType = KeyboardType.Text,
                onValueChange = onCardNameUpdate,
                onFocusChanged = onFocusChanged,
            )
            Text(
                modifier = Modifier.testTag(TestTag.CreditCardTop_CardNumberTail),
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

@Composable
internal fun CardTextField(
    value: String,
    editable: Boolean,
    textStyle: TextStyle,
    keyboardType: KeyboardType,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    maxLength: Int? = null,
    onFocusChanged: (Boolean) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    var currentValue by remember { mutableStateOf("") }
    var isFocused by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        Row {
            Text(
                modifier = Modifier.testTag(TestTag.CardTextField_Text),
                text = value,
                style = textStyle.copy(color = if (isFocused) DSTheme.colors.transparent else textStyle.color),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(modifier = Modifier.width(DSTheme.sizes.dp4))
        }
        BasicTextField(
            modifier = Modifier
                .matchParentSize()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused && isFocused) {
                        currentValue.let {
                            currentValue = ""
                            onValueChange(it)
                        }
                    } else if (focusState.isFocused && !isFocused) {
                        currentValue = ""
                    }
                    onFocusChanged(focusState.isFocused)
                    isFocused = focusState.isFocused
                },
            value = currentValue,
            enabled = editable,
            onValueChange = {
                if (
                    it.isNotEmpty() &&
                    keyboardType == KeyboardType.Number &&
                    !it.last().isDigit()
                ) {
                    // prevent non-digit symbols
                    return@BasicTextField
                }
                maxLength?.let { max ->
                    if (it.length > max) return@BasicTextField
                }
                currentValue = it
            },
            singleLine = true,
            textStyle = textStyle,
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = keyboardType,
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            )
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

@Preview
@Composable
private fun DetailedCreditCardPreview() {
    NitraTheme {
        DetailedCreditCard(
            focused = -1,
            editable = true,
            cardName = "Card Name",
            cardNumber = "0000000000001234",
            expDate = "0125",
            cvv = "123",
            onCardNameUpdate = {},
            onCardNumberUpdate = {},
            onExpDateUpdate = {},
            onCvvUpdate = {},
            onCopy = {},
            onFocusChanged = {},
        )
    }
}