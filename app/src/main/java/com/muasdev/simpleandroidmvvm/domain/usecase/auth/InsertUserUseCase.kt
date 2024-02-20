package com.muasdev.simpleandroidmvvm.domain.usecase.auth

import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.domain.repository.UserRepository
import com.muasdev.simpleandroidmvvm.utils.InvalidException
import com.muasdev.simpleandroidmvvm.utils.isValidEmail
import javax.inject.Inject

class InsertUserUseCase @Inject constructor(
    private val repository: UserRepository
) {

    @Throws(InvalidException::class)
    suspend operator fun invoke(user: User) {
        when {
            user.email.isBlank() -> {
                throw InvalidException("The email can't be empty")
            }
            !user.email.isValidEmail() -> {
                throw InvalidException("The email not valid")
            }
            user.userName.isBlank() -> {
                throw InvalidException("The username can't be empty.")
            }
            user.password.isBlank() -> {
                throw InvalidException("The password can't be empty.")
            }
            else -> repository.insertUser(user)
        }
    }
}