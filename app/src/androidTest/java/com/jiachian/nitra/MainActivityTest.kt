package com.jiachian.nitra

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.filterToOne
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.jiachian.cards.TestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Calendar

private typealias CardsTestTag = TestTag

@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test_entire_app() {
        // in the list screen - go to the form screen
        composeTestRule
            .onNodeWithTag(CardsTestTag.List_Button_AddCard)
            .performClick()

        // in the form screen - add a new card and go back to the list screen
        composeTestRule
            .onNodeWithTag(CardsTestTag.Form_TextField_CardName)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardFormBasicField_TextField))
            .performTextInput("card name")
        composeTestRule
            .onNodeWithTag(CardsTestTag.Form_TextField_NameOnCard)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardFormBasicField_TextField))
            .performTextInput("name on card")
        composeTestRule
            .onNodeWithTag(CardsTestTag.Form_TextField_CardNumber)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardFormBasicField_TextField))
            .performTextInput("0000000000000000")
        composeTestRule
            .onNodeWithTag(CardsTestTag.Form_TextField_ExpDate)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.Form_Dropdown_ExpMonth))
            .performClick()
        composeTestRule
            .onAllNodesWithTag(CardsTestTag.ExpDateDropdownMenu_MenuItem)
            .onFirst()
            .performClick()
        composeTestRule
            .onNodeWithTag(CardsTestTag.Form_TextField_ExpDate)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.Form_Dropdown_ExpYear))
            .performClick()
        composeTestRule
            .onAllNodesWithTag(CardsTestTag.ExpDateDropdownMenu_MenuItem)
            .onFirst()
            .performClick()
        composeTestRule
            .onNodeWithTag(CardsTestTag.Form_TextField_CVV)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardFormBasicField_TextField))
            .performTextInput("000")
        composeTestRule
            .onNodeWithTag(CardsTestTag.Form_Button_AddCard)
            .performClick()

        // in the list screen - click the added card and go to the detail screen
        composeTestRule
            .onAllNodesWithTag(CardsTestTag.MaskedCreditCard)
            .onFirst()
            .performClick()

        // in the detail screen - check the data of the card is correct
        composeTestRule
            .onNodeWithTag(CardsTestTag.DetailedCreditCard_CardName)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardTextField_Text))
            .assertTextEquals("card name")
        composeTestRule
            .onNodeWithTag(CardsTestTag.CreditCardTop_CardNumberTail)
            .assertTextContains("0000", substring = true)
        composeTestRule
            .onNodeWithTag(CardsTestTag.DetailedCreditCard_CardNumber)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardTextField_Text))
            .assertTextEquals("0000 0000 0000 0000")
        val yearTail = (Calendar.getInstance().get(Calendar.YEAR) + 3).toString().takeLast(2)
        composeTestRule
            .onNodeWithTag(CardsTestTag.DetailedCreditCard_ExpDate)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardTextField_Text))
            .assertTextEquals("01/$yearTail")
        composeTestRule
            .onNodeWithTag(CardsTestTag.DetailedCreditCard_CVV)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardTextField_Text))
            .assertTextEquals("000")
        composeTestRule
            .onNodeWithTag(CardsTestTag.Detail_TextField_NameOnCard)
            .onChildren()
            .filterToOne(hasTestTag(CardsTestTag.CardTextField_Text))
            .assertTextEquals("name on card")
    }
}