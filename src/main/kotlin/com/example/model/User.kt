package com.example.model

import kotlinx.serialization.Serializable

enum class Role {
    ADMIN, USER, LEADER
}

@Serializable
data class User(val id: Int? = null,
                val username: String,
                val password: String,
                val email: String,
                val role: Role,
                val isBanned: Boolean)