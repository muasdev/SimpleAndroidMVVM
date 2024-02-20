package com.muasdev.simpleandroidmvvm.domain.usecase.normal_user

import androidx.paging.PagingData
import com.muasdev.simpleandroidmvvm.domain.model.Placeholder
import com.muasdev.simpleandroidmvvm.domain.repository.PlaceholderRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAllPlaceholderUseCase @Inject constructor(
    private val repository: PlaceholderRepository
) {
    operator fun invoke(
        page: Int,
        limit: Int
    ): Flow<PagingData<Placeholder>> =
        repository.getPlaceholder(
            page, limit
        )
}