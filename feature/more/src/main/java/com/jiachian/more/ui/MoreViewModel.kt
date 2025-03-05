package com.jiachian.more.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiachian.more.domain.usecase.GetLockEnabledUseCase
import com.jiachian.more.domain.usecase.UpdateLockEnabledUseCase
import com.jiachian.more.ui.event.MoreEvent
import com.jiachian.more.ui.model.MoreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoreViewModel @Inject constructor(
    getLockEnabledUseCase: GetLockEnabledUseCase,
    private val updateLockEnabledUseCase: UpdateLockEnabledUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(MoreState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getLockEnabledUseCase().collect {
                _state.update { old ->
                    old.copy(lockEnabled = it)
                }
            }
        }
    }

    fun onEvent(event: MoreEvent) {
        when (event) {
            is MoreEvent.LockChanged -> {
                _state.update { old ->
                    old.copy(lockEnabled = event.lockEnabled)
                }
                viewModelScope.launch {
                    updateLockEnabledUseCase(event.lockEnabled)
                }
            }
        }
    }
}