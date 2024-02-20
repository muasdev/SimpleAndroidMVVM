package com.muasdev.simpleandroidmvvm.data.mapper

import com.muasdev.simpleandroidmvvm.data.source.local.entity.UserEntity
import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.utils.InvalidException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

fun Flow<List<UserEntity>>.asDomainModel(): Flow<List<User>> {
   return map { list ->
       list.map { user ->
           User(
               id = user.id,
               userName = user.userName,
               email = user.email,
               password = user.password,
               role = user.role,
           )
       }
   }
}

fun Flow<UserEntity?>.asUserDomainModel(): Flow<User> {
    return map { user ->
        if (user != null) {
            User(
                id = user.id,
                userName = user.userName,
                email = user.email,
                password = user.password,
                role = user.role
            )
        } else {
            throw InvalidException("User not found for given email and password")
        }
    }
}