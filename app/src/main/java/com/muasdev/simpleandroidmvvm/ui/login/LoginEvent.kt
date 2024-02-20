package com.muasdev.simpleandroidmvvm.ui.login

sealed class LoginEvent {

    data class UserLogin(
        val userEmail: String,
        val userPass: String
    ): LoginEvent()

    data object BackToMainPage: LoginEvent()
}