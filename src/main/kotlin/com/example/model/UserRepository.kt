package com.example.model

interface UserRepository {
    suspend fun findAll(): List<User>
    suspend fun findById(id: Int): User?
    suspend fun findByUsername(username: String): User?
    suspend fun findByEmail(email: String): User?
    suspend fun findByRole(role: Role): List<User>
    suspend fun saveUser(user: User)
    suspend fun removeUser(id: Int): Boolean
}