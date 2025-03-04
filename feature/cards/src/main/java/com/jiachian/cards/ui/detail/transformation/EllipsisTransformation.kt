package com.jiachian.cards.ui.detail.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class EllipsisTransformation(
    private val maxLength: Int,
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val ellipsis = "..."
        val transformedText = if (text.text.length > maxLength) {
            text.text.substring(0, maxLength) + ellipsis
        } else {
            text.text
        }
        return TransformedText(
            AnnotatedString(transformedText),
            object : OffsetMapping {
                override fun originalToTransformed(offset: Int): Int {
                    return if (offset <= maxLength) offset else maxLength + ellipsis.length
                }

                override fun transformedToOriginal(offset: Int): Int {
                    return if (offset <= maxLength) offset else maxLength
                }
            }
        )
    }
}