package com.muasdev.simpleandroidmvvm.ui.main.admin

sealed class AdminMainEvent {
    data object Logout: AdminMainEvent()
}