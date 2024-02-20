package com.muasdev.simpleandroidmvvm.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.muasdev.simpleandroidmvvm.data.paging.DataPagingSource
import com.muasdev.simpleandroidmvvm.data.source.remote.PlaceholderApiServices
import com.muasdev.simpleandroidmvvm.domain.model.Placeholder
import com.muasdev.simpleandroidmvvm.domain.repository.PlaceholderRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PlaceholderRepositoryImpl @Inject constructor(
    private val apiServices: PlaceholderApiServices
): PlaceholderRepository {

    override fun getPlaceholder(page: Int, limit: Int): Flow<PagingData<Placeholder>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                DataPagingSource(apiServices)
            }
        ).flow
    }


}