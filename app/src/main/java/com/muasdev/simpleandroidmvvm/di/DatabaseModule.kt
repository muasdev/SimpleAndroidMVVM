package com.muasdev.simpleandroidmvvm.di

import android.content.Context
import androidx.room.Room
import com.muasdev.simpleandroidmvvm.data.source.local.dao.UserDao
import com.muasdev.simpleandroidmvvm.data.source.local.db.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase): UserDao =
        database.userDao

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): UserDatabase = Room.databaseBuilder(
        context,
        UserDatabase::class.java,
        "user_db"
    ).build()
}