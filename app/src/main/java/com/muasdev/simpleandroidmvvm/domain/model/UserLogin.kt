package com.muasdev.simpleandroidmvvm.domain.model

data class UserLogin(
    val email: String,
    val password: String,
    val role: UserRole
)