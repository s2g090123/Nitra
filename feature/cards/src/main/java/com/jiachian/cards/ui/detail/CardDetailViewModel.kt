package com.jiachian.cards.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.jiachian.cards.domain.model.Card
import com.jiachian.cards.domain.usecase.GetCardUseCase
import com.jiachian.cards.domain.usecase.UpdateCardUseCase
import com.jiachian.cards.ui.detail.event.CardDetailAction
import com.jiachian.cards.ui.detail.event.CardDetailEvent
import com.jiachian.cards.ui.detail.model.CardDetailState
import com.jiachian.cards.ui.route.CardsRoute
import com.jiachian.common.domain.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CardDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCardUseCase: GetCardUseCase,
    private val updateCardUseCase: UpdateCardUseCase,
) : ViewModel() {
    private val cardId = savedStateHandle.toRoute<CardsRoute.Detail>().id

    private val _state = MutableStateFlow(CardDetailState())
    val state = _state.asStateFlow()

    private var expYearHead = ""

    init {
        viewModelScope.launch {
            getCardUseCase(cardId).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update { old ->
                            old.copy(
                                loading = false,
                                action = resource.t?.message?.let {
                                    CardDetailAction.ShowErrorMessage(it)
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
                        expYearHead = resource.data.expYear.dropLast(2)
                        _state.update { old ->
                            old.copy(
                                loading = false,
                                cardName = resource.data.cardName,
                                nameOnCard = resource.data.nameOnCard,
                                cardNumber = resource.data.cardNumber,
                                expDate = resource.data.expMonth + resource.data.expYear.takeLast(2),
                                cvv = resource.data.cvv
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: CardDetailEvent) {
        when (event) {
            CardDetailEvent.ConsumeAction -> {
                _state.update { old ->
                    old.copy(action = null)
                }
            }

            is CardDetailEvent.UpdateCardName -> updateCardName(event.value)
            is CardDetailEvent.UpdateCardNumber -> updateCardNumber(event.value)
            is CardDetailEvent.UpdateCvv -> updateCvv(event.value)
            is CardDetailEvent.UpdateNameOnCard -> updateNameOnCard(event.value)
            is CardDetailEvent.UpdateExpDate -> updateExpDate(event.value)
        }
    }

    private fun updateCardName(value: String) {
        if (value.isEmpty()) {
            _state.update { old ->
                old.copy(
                    action = CardDetailAction.ShowErrorMessage("Card name cannot be empty.")
                )
            }
            return
        }

        val card = getCard().copy(cardName = value)
        viewModelScope.launch {
            updateCardUseCase(card).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update { old ->
                            old.copy(
                                loading = false,
                                action = resource.t?.message?.let {
                                    CardDetailAction.ShowErrorMessage(it)
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
                                loading = false,
                                cardName = value,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateNameOnCard(value: String) {
        if (value.isEmpty()) {
            _state.update { old ->
                old.copy(
                    action = CardDetailAction.ShowErrorMessage("Name cannot be empty.")
                )
            }
            return
        }

        val card = getCard().copy(nameOnCard = value)
        viewModelScope.launch {
            updateCardUseCase(card).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update { old ->
                            old.copy(
                                loading = false,
                                action = resource.t?.message?.let {
                                    CardDetailAction.ShowErrorMessage(it)
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
                                loading = false,
                                nameOnCard = value,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateCardNumber(value: String) {
        if (value.length != 16) {
            _state.update { old ->
                old.copy(
                    action = CardDetailAction.ShowErrorMessage("Card number is invalid.")
                )
            }
            return
        }

        val card = getCard().copy(cardNumber = value)
        viewModelScope.launch {
            updateCardUseCase(card).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update { old ->
                            old.copy(
                                loading = false,
                                action = resource.t?.message?.let {
                                    CardDetailAction.ShowErrorMessage(it)
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
                                loading = false,
                                cardNumber = value,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateCvv(value: String) {
        if (value.length != 3) {
            _state.update { old ->
                old.copy(
                    action = CardDetailAction.ShowErrorMessage("CVV is invalid.")
                )
            }
            return
        }

        val card = getCard().copy(cvv = value)
        viewModelScope.launch {
            updateCardUseCase(card).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update { old ->
                            old.copy(
                                loading = false,
                                action = resource.t?.message?.let {
                                    CardDetailAction.ShowErrorMessage(it)
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
                                loading = false,
                                cvv = value,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun updateExpDate(value: String) {
        if (value.length != 4) {
            _state.update { old ->
                old.copy(
                    action = CardDetailAction.ShowErrorMessage("Exp Date is invalid.")
                )
            }
            return
        }

        val month = value.take(2)
        val year = value.takeLast(2)
        if (month.toInt() !in 1..12) {
            _state.update { old ->
                old.copy(
                    action = CardDetailAction.ShowErrorMessage("Exp Date is invalid.")
                )
            }
            return
        }

        val card = getCard().copy(
            expYear = expYearHead + year,
            expMonth = month,
        )
        viewModelScope.launch {
            updateCardUseCase(card).collect { resource ->
                when (resource) {
                    is Resource.Error -> {
                        _state.update { old ->
                            old.copy(
                                loading = false,
                                action = resource.t?.message?.let {
                                    CardDetailAction.ShowErrorMessage(it)
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
                                loading = false,
                                expDate = value,
                            )
                        }
                    }
                }
            }
        }
    }

    private fun getCard(): Card {
        return Card(
            id = cardId,
            cardName = state.value.cardName,
            nameOnCard = state.value.nameOnCard,
            cardNumber = state.value.cardNumber,
            expYear = state.value.expDate.takeLast(2),
            expMonth = state.value.expDate.take(2),
            cvv = state.value.cvv,
        )
    }
}