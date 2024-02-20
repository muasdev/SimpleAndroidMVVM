package com.muasdev.simpleandroidmvvm.di

import android.content.Context
import com.muasdev.simpleandroidmvvm.data.source.remote.PlaceholderApiServices
import com.muasdev.simpleandroidmvvm.utils.NetworkConnectivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val interceptor = run {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()


    @Singleton
    @Provides
    fun provideRetrofitService(okHttpClient: OkHttpClient): PlaceholderApiServices =
        Retrofit.Builder()
            .baseUrl(PlaceholderApiServices.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(okHttpClient)
            .build()
            .create(PlaceholderApiServices::class.java)

    @Provides
    @Singleton
    fun provideNetworkConnectivity(
        @ApplicationContext context: Context,
    ): NetworkConnectivity {
        return NetworkConnectivity(context)
    }
}