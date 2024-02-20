package com.muasdev.simpleandroidmvvm.data.source.remote

import com.muasdev.simpleandroidmvvm.domain.model.Placeholder
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaceholderApiServices {

    @GET("photos")
    suspend fun getData(
        @Query("_page") page: Int,
        @Query("_limit") limit: Int,
    ): List<Placeholder>

    companion object {
        const val BASE_URL: String = "https://jsonplaceholder.typicode.com/"
    }
}
