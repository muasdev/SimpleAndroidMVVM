package com.muasdev.simpleandroidmvvm.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

private const val PREFERENCES_NAME = "app_preferences"

val Context.dataStore by preferencesDataStore(
    name = PREFERENCES_NAME
)

val keyIsLogin = booleanPreferencesKey("is_login")
val keyUserEmail = stringPreferencesKey("user_email")
val keyUserPassword = stringPreferencesKey("user_password")
val keyUserRole = stringPreferencesKey("user_role")