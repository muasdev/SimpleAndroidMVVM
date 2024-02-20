package com.muasdev.simpleandroidmvvm.ui.main.user

sealed class UserEvent {
    data object Logout: UserEvent()
}