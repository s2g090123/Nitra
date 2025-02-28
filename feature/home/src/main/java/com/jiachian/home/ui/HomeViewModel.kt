package com.jiachian.home.ui

import androidx.lifecycle.ViewModel
import com.jiachian.home.ui.intents.HomeEvent
import com.jiachian.home.ui.states.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.NavigateTo -> {
                _state.update { old ->
                    old.copy(route = event.route)
                }
            }
        }
    }
}