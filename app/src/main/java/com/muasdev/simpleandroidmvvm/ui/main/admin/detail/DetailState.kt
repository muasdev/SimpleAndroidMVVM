package com.muasdev.simpleandroidmvvm.ui.main.admin.detail

import com.muasdev.simpleandroidmvvm.domain.model.User

data class DetailState (
    val user: User? = null,
    val userUpdatedSuccessfully: Boolean? = false,
    val userDeletedSuccessfully: Boolean? = false,
    val isNavigateToAdminPage: Boolean = false,
    val errorMessage: String? = null,
)