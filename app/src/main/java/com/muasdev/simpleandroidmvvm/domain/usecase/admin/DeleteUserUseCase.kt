package com.muasdev.simpleandroidmvvm.domain.usecase.admin

import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.domain.repository.UserRepository
import javax.inject.Inject

class DeleteUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(
        user: User
    ) = repository.deleteUser(user)
}