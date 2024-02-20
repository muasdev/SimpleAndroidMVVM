package com.muasdev.simpleandroidmvvm.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.muasdev.simpleandroidmvvm.data.source.remote.PlaceholderApiServices
import com.muasdev.simpleandroidmvvm.domain.model.Placeholder
import okio.IOException
import retrofit2.HttpException

const val STARTING_PAGE_INDEX = 1

class DataPagingSource(
    private val apiServices: PlaceholderApiServices,
) : PagingSource<Int, Placeholder>() {
    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Placeholder> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = apiServices.getData(
                page = pageIndex,
                limit = 10
            )
            val nextKey =
                if (response.isEmpty()) {
                    null
                } else {
                    pageIndex + 1
                }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Placeholder>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}