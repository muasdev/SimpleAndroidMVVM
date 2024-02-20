package com.muasdev.simpleandroidmvvm.ui.login

import com.muasdev.simpleandroidmvvm.domain.model.User

data class LoginState (
    val user: User? = null,
    val backToPrevPage: Boolean = false,
    val errorMessage: String? = null,
)