package com.muasdev.simpleandroidmvvm.ui.main

sealed class MainEvent {
    data object CheckUserAuth : MainEvent()
}