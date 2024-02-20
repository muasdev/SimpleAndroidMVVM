package com.muasdev.simpleandroidmvvm.domain.usecase.admin

import com.muasdev.simpleandroidmvvm.domain.repository.UserRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: Int) = repository.getUser(userId)
}