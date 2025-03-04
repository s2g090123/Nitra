package com.jiachian.cards.ui.detail.transformation

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

/**
 * A VisualTransformation that formats a 4-digit expiration date input into the "MM/YY" format.
 *
 * Example:
 * - Input: "1224" → Output: "12/24"
 * - Input: "07" → Output: "07"
 *
 * This transformation ensures that:
 * 1. A slash (`/`) is inserted after the first two characters.
 * 2. The cursor movement is correctly adjusted to account for the added `/`.
 */
class ExpDateTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = text.text
        // Format the text: Insert "/" after the first two characters if input length is 3 or 4
        val formatted = when (transformedText.length) {
            in 3..4 -> transformedText.substring(0, 2) + "/" + transformedText.substring(2)
            else -> transformedText
        }

        return TransformedText(
            AnnotatedString(formatted),
            object : OffsetMapping {
                /**
                 * Maps the original cursor position to the transformed text.
                 * Ensures that characters after position 2 are shifted by 1 due to the inserted "/".
                 */
                override fun originalToTransformed(offset: Int): Int {
                    return when {
                        offset <= 2 -> offset
                        offset in 3..4 -> offset + 1
                        else -> 5 // Ensures that the cursor does not go beyond the expected length
                    }
                }

                /**
                 * Maps the cursor position from transformed text back to the original text.
                 * Removes the effect of the inserted "/" when navigating back.
                 */
                override fun transformedToOriginal(offset: Int): Int {
                    return when {
                        offset <= 2 -> offset
                        offset in 3..5 -> offset - 1
                        else -> 4 // Ensures that the cursor stays within bounds
                    }
                }
            }
        )
    }
}