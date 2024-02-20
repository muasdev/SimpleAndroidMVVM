package com.muasdev.simpleandroidmvvm.domain.repository

import com.muasdev.simpleandroidmvvm.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {

    suspend fun getUsers(): Flow<List<User>>
    suspend fun getUser(id: Int): Flow<User>
    suspend fun insertUser(user: User)
    suspend fun updateUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun verifyUserLogin(
        email: String, password: String
    ): Flow<User>
}