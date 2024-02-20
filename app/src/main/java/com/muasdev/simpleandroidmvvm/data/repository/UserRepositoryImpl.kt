package com.muasdev.simpleandroidmvvm.data.repository

import com.muasdev.simpleandroidmvvm.data.mapper.asDomainModel
import com.muasdev.simpleandroidmvvm.data.mapper.asUserDomainModel
import com.muasdev.simpleandroidmvvm.data.source.local.dao.UserDao
import com.muasdev.simpleandroidmvvm.domain.model.User
import com.muasdev.simpleandroidmvvm.domain.model.toLocal
import com.muasdev.simpleandroidmvvm.domain.repository.UserRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): UserRepository {

    override suspend fun getUsers(): Flow<List<User>> {
        return userDao.getUsers().asDomainModel()
    }

    override suspend fun getUser(id: Int): Flow<User> {
        return userDao.getUser(id).asUserDomainModel()
    }

    override suspend fun insertUser(user: User) {
        return userDao.insertUser(user = user.toLocal())
    }

    override suspend fun updateUser(user: User) {
        return userDao.update(user = user.toLocal())
    }

    override suspend fun deleteUser(user: User) {
        return userDao.delete(user = user.toLocal())
    }

    override suspend fun verifyUserLogin(email: String, password: String): Flow<User> {
        return userDao.verifyUser(email, password).asUserDomainModel()
    }

}