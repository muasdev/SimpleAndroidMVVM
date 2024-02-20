package com.muasdev.simpleandroidmvvm.domain.repository

import androidx.paging.PagingData
import com.muasdev.simpleandroidmvvm.domain.model.Placeholder
import kotlinx.coroutines.flow.Flow

interface PlaceholderRepository {
    fun getPlaceholder(page: Int, limit: Int): Flow<PagingData<Placeholder>>
}