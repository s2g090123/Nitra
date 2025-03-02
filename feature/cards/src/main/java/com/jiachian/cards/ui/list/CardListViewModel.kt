package com.jiachian.cards.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiachian.cards.domain.usecase.GetCardsUseCase
import com.jiachian.cards.ui.list.model.CardListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CardListViewModel @Inject constructor(
    getCardsUseCase: GetCardsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CardListState(loading = true))
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCardsUseCase().collect { cards ->
                _state.update { old ->
                    old.copy(
                        loading = false,
                        cards = cards,
                    )
                }
            }
        }
    }
}