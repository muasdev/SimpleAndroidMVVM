package com.muasdev.simpleandroidmvvm.ui.main.admin

import com.muasdev.simpleandroidmvvm.domain.model.User

data class AdminMainState (
    val users: List<User> = emptyList(),
    val userLogout: Boolean = false,
    val errorMessage: String? = null,
)