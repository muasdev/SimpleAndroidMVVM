package com.muasdev.simpleandroidmvvm.data.source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.muasdev.simpleandroidmvvm.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_tbl ORDER BY id ASC")
    fun getUsers(): Flow<List<UserEntity>>

    @Query("SELECT * from user_tbl WHERE id = :id")
    fun getUser(id: Int): Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM user_tbl WHERE email LIKE :email AND password LIKE :password")
    fun verifyUser(email: String, password: String): Flow<UserEntity?>

    @Update
    suspend fun update(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)
}