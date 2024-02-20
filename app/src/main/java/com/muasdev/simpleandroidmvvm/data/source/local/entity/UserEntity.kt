package com.muasdev.simpleandroidmvvm.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.muasdev.simpleandroidmvvm.domain.model.UserRole

@Entity(tableName = "user_tbl")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val userName: String,
    val email: String,
    val password: String,
    val role: UserRole
)