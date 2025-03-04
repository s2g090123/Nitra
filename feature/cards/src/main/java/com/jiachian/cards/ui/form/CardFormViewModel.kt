package com.jiachian.cards.ui.form

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiachian.cards.domain.model.Card
import com.jiachian.cards.domain.usecase.AddCardUseCase
import com.jiachian.cards.domain.usecase.ValidateCardUseCase
import com.jiachian.cards.ui.form.event.CardFormAction
import com.jiachian.cards.ui.form.event.CardFormEvent
import com.jiachian.cards.ui.form.model.CardFormState
import com.jiachian.common.domain.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CardFormViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase,
    private val validateCardUseCase: ValidateCardUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CardFormState())
    val state = _state.asStateFlow()

    fun onEvent(event: CardFormEvent) {
        when (event) {
            CardFormEvent.AddCard -> {
                addCard()
            }

            CardFormEvent.ConsumeAction -> {
                _state.update { old ->
                    old.copy(action = null)
                }
            }

            is CardFormEvent.UpdateCardName -> {
                _state.update { old ->
                    old.copy(
                        cardName = event.value,
                        cardNameValid = true,
                    )
                }
            }

            is CardFormEvent.UpdateCardNumber -> {
                _state.update { old ->
                    old.copy(
                        cardNumber = event.value,
                        cardNumberValid = true,
                    )
                }
            }

            is CardFormEvent.UpdateCvv -> {
                _state.update { old ->
                    old.copy(
                        cvv = event.value,
                        cvvValid = true,
                    )
                }
            }

            is CardFormEvent.UpdateExpMonth -> {
                _state.update { old ->
                    old.copy(
                        expMonth = event.value,
                        expMonthValid = true,
                    )
                }
            }

            is CardFormEvent.UpdateExpYear -> {
                _state.update { old ->
                    old.copy(
                        expYear = event.value,
                        expYearValid = true,
                    )
                }
            }

            is CardFormEvent.UpdateNameOnCard -> {
                _state.update { old ->
                    old.copy(
                        nameOnCard = event.value,
                        nameOnCardValid = true,
                    )
                }
            }
        }
    }

    private fun addCard() {
        val card = getCard()

        val result = validateCardUseCase(card)
        _state.update { old ->
            old.copy(
                cardNameValid = result.cardNameValid,
                nameOnCardValid = result.nameOnCardValid,
                cardNumberValid = result.cardNumberValid,
                expMonthValid = result.expMonthValid,
                expYearValid = result.expYearValid,
                cvvValid = result.cvvValid,
            )
        }
        if (!result.isValid) {
            return
        }

        viewModelScope.launch {
            addCardUseCase(card).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update { old ->
                            old.copy(
                                loading = false,
                                action = resource.t?.message?.let {
                                    CardFormAction.ShowErrorMessage(it)
                                }
                            )
                        }
                    }

                    is Resource.Loading -> {
                        _state.update { old ->
                            old.copy(loading = true)
                        }
                    }

                    is Resource.Success -> {
                        _state.update { old ->
                            old.copy(
                                action = CardFormAction.GoToList,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCard(): Card {
        return Card(
            id = 0,
            cardName = state.value.cardName,
            nameOnCard = state.value.nameOnCard,
            cardNumber = state.value.cardNumber,
            expMonth = state.value.expMonth,
            expYear = state.value.expYear,
            cvv = state.value.cvv,
        )
    }
}