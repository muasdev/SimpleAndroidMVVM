package com.muasdev.simpleandroidmvvm.domain.model

import com.muasdev.simpleandroidmvvm.data.source.local.entity.UserEntity

data class User(
    val id: Int? = null,
    val userName: String,
    val email: String,
    val password: String,
    val role: UserRole
)

enum class UserRole {
    ADMIN, USER
}

fun User.toLocal() = UserEntity(
    id = id,
    userName = userName,
    email = email,
    password = password,
    role = role
)
