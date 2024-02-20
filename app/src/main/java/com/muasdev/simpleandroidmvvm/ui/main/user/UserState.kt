package com.muasdev.simpleandroidmvvm.ui.main.user

import androidx.paging.PagingData
import com.muasdev.simpleandroidmvvm.domain.model.Placeholder

data class UserState (
    val pagingData: PagingData<Placeholder>? = null,
    val userLogout: Boolean = false,
    val errorMessage: String? = null,
)