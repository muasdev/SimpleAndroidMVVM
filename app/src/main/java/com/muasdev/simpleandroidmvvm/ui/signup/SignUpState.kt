package com.muasdev.simpleandroidmvvm.ui.signup

data class SignUpState (
    val userSignUpSuccessfully: Boolean? = false,
    val backToPrevPage: Boolean = false,
    val errorMessage: String? = null,
)