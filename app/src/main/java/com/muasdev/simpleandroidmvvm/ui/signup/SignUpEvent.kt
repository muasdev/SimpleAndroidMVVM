package com.muasdev.simpleandroidmvvm.ui.signup

import com.muasdev.simpleandroidmvvm.domain.model.User

sealed class SignUpEvent {

    data class InsertUser(
        val user: User
    ): SignUpEvent()

    data object BackToLoginPage: SignUpEvent()
}