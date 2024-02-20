package com.muasdev.simpleandroidmvvm.domain.usecase.admin

import com.muasdev.simpleandroidmvvm.domain.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.getUsers()
}