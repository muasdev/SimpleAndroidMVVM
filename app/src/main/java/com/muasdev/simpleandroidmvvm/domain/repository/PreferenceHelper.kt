package com.muasdev.simpleandroidmvvm.domain.repository

import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.domain.model.UserLogin

interface PreferenceHelper {
    suspend fun setUserIsLogin(user: User)
    suspend fun isUserLogin(): Boolean
    suspend fun setUserLogout()
    suspend fun getUserLoginInfo(): UserLogin
}