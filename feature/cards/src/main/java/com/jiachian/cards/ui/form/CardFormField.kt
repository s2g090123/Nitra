package com.jiachian.cards.ui.form

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.jiachian.cards.R
import com.jiachian.cards.TestTag
import com.jiachian.common.ui.DSTheme
import com.jiachian.common.ui.NitraTheme
import java.util.Calendar

@Composable
internal fun CardFormBasicField(
    title: String,
    hint: String,
    value: String,
    error: Boolean,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLength: Int? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
) {
    val focusManager = LocalFocusManager.current
    var text by rememberSaveable { mutableStateOf(value) }
    var isFocused by remember { mutableStateOf(false) }
    var isError by remember(value, error) { mutableStateOf(error) }

    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = DSTheme.fonts.medium14.copy(color = DSTheme.colors.black),
        )
        Spacer(modifier = Modifier.height(DSTheme.sizes.dp8))
        OutlinedTextField(
            modifier = Modifier
                .testTag(TestTag.CardFormBasicField_TextField)
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    if (!focusState.isFocused && isFocused) {
                        onValueChange(text)
                    }
                    isFocused = focusState.isFocused
                },
            value = text,
            onValueChange = {
                if (
                    it.isNotEmpty() &&
                    keyboardOptions.keyboardType == KeyboardType.Number &&
                    !it.last().isDigit()
                ) {
                    // prevent non-digit symbols
                    return@OutlinedTextField
                }
                if (it.length <= (maxLength ?: Int.MAX_VALUE)) {
                    val originalLength = text.length
                    text = it
                    isError = false
                    if (it.length == maxLength && originalLength == maxLength - 1) {
                        // Automatically focus on the next field when the input reaches its maximum length
                        when (keyboardOptions.imeAction) {
                            ImeAction.Done -> focusManager.clearFocus()
                            ImeAction.Next -> focusManager.moveFocus(FocusDirection.Next)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(DSTheme.sizes.dp4),
            singleLine = true,
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = DSTheme.colors.primary,
                focusedBorderColor = DSTheme.colors.black,
                unfocusedBorderColor = DSTheme.colors.gray200Light,
                errorBorderColor = DSTheme.colors.red,
            ),
            textStyle = DSTheme.fonts.regular16.copy(color = DSTheme.colors.black),
            placeholder = {
                Text(
                    text = hint,
                    style = DSTheme.fonts.regular16.copy(color = DSTheme.colors.gray400),
                )
            },
            keyboardOptions = keyboardOptions,
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                },
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                },
            ),
            visualTransformation = visualTransformation,
        )
    }
}

@Composable
internal fun CardFormDateField(
    title: String,
    valueForYear: String,
    valueForMonth: String,
    hintForYear: String,
    hintForMonth: String,
    errorForYear: Boolean,
    errorForMonth: Boolean,
    onValueChangeForYear: (String) -> Unit,
    onValueChangeForMonth: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val calendar = remember { Calendar.getInstance() }
    val currentYear = remember { calendar.get(Calendar.YEAR) }

    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = DSTheme.fonts.medium14.copy(color = DSTheme.colors.black),
        )
        Spacer(modifier = Modifier.height(DSTheme.sizes.dp8))
        Row(
            horizontalArrangement = Arrangement.spacedBy(DSTheme.sizes.dp8)
        ) {
            ExpDateDropdownMenu(
                modifier = Modifier
                    .testTag(TestTag.Form_Dropdown_ExpMonth)
                    .weight(1f),
                items = List(12) { i -> "%02d".format(i + 1) },
                value = valueForMonth,
                hint = hintForMonth,
                error = errorForMonth,
                onItemClick = onValueChangeForMonth,
            )
            ExpDateDropdownMenu(
                modifier = Modifier
                    .testTag(TestTag.Form_Dropdown_ExpYear)
                    .weight(1f),
                items = List(8) { i -> (currentYear + 3 + i).toString() },
                value = valueForYear,
                hint = hintForYear,
                error = errorForYear,
                onItemClick = onValueChangeForYear,
            )
        }
    }
}

@Composable
private fun ExpDateDropdownMenu(
    items: List<String>,
    value: String,
    hint: String,
    error: Boolean,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusManager = LocalFocusManager.current
    val density = LocalDensity.current
    var expanded by remember { mutableStateOf(false) }
    var menuWidth by remember { mutableStateOf(Dp.Unspecified) }
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(DSTheme.sizes.dp4))
            .clickable {
                expanded = true
                focusManager.clearFocus()
            }
            .border(
                DSTheme.sizes.dp1,
                if (error) DSTheme.colors.red else DSTheme.colors.gray200Light,
                RoundedCornerShape(DSTheme.sizes.dp4),
            )
            .onSizeChanged { with(density) { menuWidth = it.width.toDp() } },
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = DSTheme.sizes.dp16, vertical = DSTheme.sizes.dp14),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (value.isEmpty()) {
                Text(
                    text = hint,
                    style = DSTheme.fonts.regular16.copy(color = DSTheme.colors.gray400),
                )
            } else {
                Text(
                    text = value,
                    style = DSTheme.fonts.regular16.copy(color = DSTheme.colors.black),
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_arrow_down),
                contentDescription = null,
                tint = DSTheme.colors.black,
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { option ->
                DropdownMenuItem(
                    modifier = Modifier
                        .testTag(TestTag.ExpDateDropdownMenu_MenuItem)
                        .width(menuWidth),
                    text = {
                        Text(
                            text = option,
                            style = DSTheme.fonts.regular16.copy(color = DSTheme.colors.black),
                        )
                    },
                    onClick = {
                        onItemClick(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun CardFormBasicFieldPreview() {
    NitraTheme {
        CardFormBasicField(
            title = "Card Name",
            hint = "Wei’s Card",
            value = "",
            error = false,
            keyboardOptions = KeyboardOptions.Default,
            onValueChange = {},
        )
    }
}

@Preview
@Composable
private fun CardFormDateFieldPreview() {
    NitraTheme {
        CardFormDateField(
            title = "Exp Date",
            valueForYear = "",
            valueForMonth = "",
            hintForYear = "Year",
            hintForMonth = "Month",
            errorForYear = false,
            errorForMonth = false,
            onValueChangeForYear = {},
            onValueChangeForMonth = {},
        )
    }
}