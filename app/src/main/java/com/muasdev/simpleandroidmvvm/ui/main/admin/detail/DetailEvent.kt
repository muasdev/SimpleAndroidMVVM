package com.muasdev.simpleandroidmvvm.ui.main.admin.detail

import com.muasdev.simpleandroidmvvm.domain.model.User

sealed class DetailEvent {

    data class GetUserById(
        val userId: Int
    ): DetailEvent()

    data class UpdateUser(
        val user: User
    ): DetailEvent()

    data class RemoveUser(
        val user: User,
        val passwordAdmin: String
    ): DetailEvent()

    data object BackToAdminPage: DetailEvent()
}