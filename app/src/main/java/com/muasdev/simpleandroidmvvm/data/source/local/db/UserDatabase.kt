package com.muasdev.simpleandroidmvvm.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.muasdev.simpleandroidmvvm.data.source.local.dao.UserDao
import com.muasdev.simpleandroidmvvm.data.source.local.entity.UserEntity

@Database(
    entities = [UserEntity::class],
    version = 1
)
abstract class UserDatabase: RoomDatabase() {
    abstract val userDao: UserDao
}