package com.jiachian.cards.ui.form

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.jiachian.cards.R
import com.jiachian.cards.ui.form.event.CardFormAction
import com.jiachian.cards.ui.form.event.CardFormEvent
import com.jiachian.cards.ui.form.model.CardFormState
import com.jiachian.cards.ui.form.transformation.CardNumberTransformation
import com.jiachian.common.ui.DSTheme
import com.jiachian.common.ui.NitraTheme

@Composable
internal fun CardFormScreen(
    state: CardFormState,
    onEvent: (CardFormEvent) -> Unit,
    onBackClick: () -> Unit,
    goToList: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(horizontal = DSTheme.sizes.dp16, vertical = DSTheme.sizes.dp12)
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
                text = stringResource(R.string.card_form_title),
                style = DSTheme.fonts.semiBold18.copy(color = DSTheme.colors.black),
                overflow = TextOverflow.Ellipsis,
            )
        }
        CardFormBasicField(
            modifier = Modifier
                .padding(top = DSTheme.sizes.dp32)
                .fillMaxWidth(),
            title = stringResource(R.string.card_form_option_card_name),
            hint = stringResource(R.string.card_form_option_card_name_hint),
            value = state.cardName,
            error = !state.cardNameValid,
            onValueChange = { onEvent(CardFormEvent.UpdateCardName(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
        )
        CardFormBasicField(
            modifier = Modifier
                .padding(top = DSTheme.sizes.dp4)
                .fillMaxWidth(),
            title = stringResource(R.string.card_form_option_name_on_card),
            hint = stringResource(R.string.card_form_option_name_on_card_hint),
            value = state.nameOnCard,
            error = !state.nameOnCardValid,
            onValueChange = { onEvent(CardFormEvent.UpdateNameOnCard(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next,
            ),
        )
        CardFormBasicField(
            modifier = Modifier
                .padding(top = DSTheme.sizes.dp4)
                .fillMaxWidth(),
            title = stringResource(R.string.card_form_option_card_number),
            hint = stringResource(R.string.card_form_option_card_number_hint),
            value = state.cardNumber,
            error = !state.cardNumberValid,
            maxLength = 16,
            onValueChange = { onEvent(CardFormEvent.UpdateCardNumber(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next,
            ),
            visualTransformation = CardNumberTransformation(),
        )
        CardFormDateField(
            modifier = Modifier
                .padding(top = DSTheme.sizes.dp4)
                .fillMaxWidth(),
            title = stringResource(R.string.card_form_option_exp_date),
            valueForYear = state.expYear,
            valueForMonth = state.expMonth,
            hintForYear = stringResource(R.string.card_form_option_exp_date_year_hint),
            hintForMonth = stringResource(R.string.card_form_option_exp_date_month_hint),
            errorForYear = !state.expYearValid,
            errorForMonth = !state.expMonthValid,
            onValueChangeForYear = { onEvent(CardFormEvent.UpdateExpYear(it)) },
            onValueChangeForMonth = { onEvent(CardFormEvent.UpdateExpMonth(it)) },
        )
        CardFormBasicField(
            modifier = Modifier
                .padding(top = DSTheme.sizes.dp4)
                .fillMaxWidth(),
            title = stringResource(R.string.card_form_option_cvv),
            hint = stringResource(R.string.card_form_option_cvv_hint),
            value = state.cvv,
            error = !state.cvvValid,
            maxLength = 3,
            onValueChange = { onEvent(CardFormEvent.UpdateCvv(it)) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done,
            ),
        )
        Button(
            modifier = Modifier
                .padding(top = DSTheme.sizes.dp32)
                .fillMaxWidth(),
            shape = RoundedCornerShape(DSTheme.sizes.dp4),
            colors = ButtonDefaults.buttonColors(containerColor = DSTheme.colors.orange400),
            contentPadding = PaddingValues(DSTheme.sizes.dp14),
            onClick = {
                focusManager.clearFocus()
                onEvent(CardFormEvent.AddCard)
            },
        ) {
            Text(
                text = stringResource(R.string.card_form_add_card),
                style = DSTheme.fonts.semiBold16.copy(color = DSTheme.colors.white),
            )
        }
    }

    LaunchedEffect(state.action) {
        state.action?.let { action ->
            when (action) {
                CardFormAction.GoToList -> goToList()
                is CardFormAction.ShowErrorMessage -> Toast.makeText(
                    context,
                    action.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            onEvent(CardFormEvent.ConsumeAction)
        }
    }
}

@Preview
@Composable
private fun CardFormScreenPreview() {
    NitraTheme {
        CardFormScreen(
            state = CardFormState(),
            onEvent = {},
            onBackClick = {},
            goToList = {},
        )
    }
}