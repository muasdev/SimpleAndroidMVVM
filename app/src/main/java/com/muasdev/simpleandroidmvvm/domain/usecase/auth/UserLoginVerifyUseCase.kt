package com.muasdev.simpleandroidmvvm.domain.usecase.auth

import com.muasdev.simpleandroidmvvm.domain.repository.UserRepository
import javax.inject.Inject

class UserLoginVerifyUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        userEmail: String, userPass: String
    ) = repository.verifyUserLogin(userEmail, userPass)
}