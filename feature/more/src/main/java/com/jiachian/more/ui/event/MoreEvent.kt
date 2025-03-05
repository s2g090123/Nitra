package com.jiachian.more.ui.event

sealed interface MoreEvent {
    data class LockChanged(val lockEnabled: Boolean) : MoreEvent
}