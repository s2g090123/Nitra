package com.jiachian.nitra

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jiachian.more.domain.usecase.GetLockEnabledUseCase
import com.jiachian.nitra.ui.model.MainState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    getLockEnabledUseCase: GetLockEnabledUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
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
}