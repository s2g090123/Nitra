package com.jiachian.cards.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CardsState())
    val state = _state.asStateFlow()
}