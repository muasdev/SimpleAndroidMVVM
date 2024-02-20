package com.muasdev.simpleandroidmvvm.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.domain.model.UserLogin
import com.muasdev.simpleandroidmvvm.domain.model.UserRole
import com.muasdev.simpleandroidmvvm.domain.repository.PreferenceHelper
import com.muasdev.simpleandroidmvvm.utils.keyIsLogin
import com.muasdev.simpleandroidmvvm.utils.keyUserEmail
import com.muasdev.simpleandroidmvvm.utils.keyUserPassword
import com.muasdev.simpleandroidmvvm.utils.keyUserRole
import javax.inject.Inject
import kotlinx.coroutines.flow.first

class PreferenceHelperImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
): PreferenceHelper {

    override suspend fun setUserIsLogin(user: User) {
        val userRole = if (user.role == UserRole.USER) {
            UserRole.USER
        } else {
            UserRole.ADMIN
        }
        dataStore.edit { pref ->
            pref[keyIsLogin] = true
            pref[keyUserEmail] = user.email
            pref[keyUserPassword] = user.password
            pref[keyUserRole] = userRole.toString()
        }
    }

    override suspend fun isUserLogin(): Boolean {
        val pref = dataStore.data.first()
        return pref[keyIsLogin] ?: false
    }

    override suspend fun setUserLogout() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }

    override suspend fun getUserLoginInfo(): UserLogin {
        val pref = dataStore.data.first()
        val userEmail = pref[keyUserEmail]
        val userPassword = pref[keyUserPassword]
        val userRole = pref[keyUserRole]
        val role = if (userRole == UserRole.USER.toString()) {
            UserRole.USER
        } else {
            UserRole.ADMIN
        }

        return UserLogin(
            userEmail ?: "",
            userPassword ?: "",
            role = role
        )
    }

}