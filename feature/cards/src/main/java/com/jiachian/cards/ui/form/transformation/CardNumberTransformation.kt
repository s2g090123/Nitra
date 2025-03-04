package com.jiachian.cards.ui.form.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * To format display for the card number, add space every 4 characters.
 * e.g. 0000000000000000 -> 0000 0000 0000 0000
 */
internal class CardNumberTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val formattedText = text.text.chunked(4).joinToString(" ")

        return TransformedText(
            text = AnnotatedString(formattedText),
            offsetMapping = object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    val spacesBefore = offset / 4
                    val transformedOffset = offset + spacesBefore
                    return transformedOffset.coerceAtMost(formattedText.length)
                }

                override fun transformedToOriginal(offset: Int): Int {
                    val spacesBefore = offset / 5
                    val originalOffset = offset - spacesBefore
                    return originalOffset.coerceAtMost(text.text.length)
                }
            })
    }
}