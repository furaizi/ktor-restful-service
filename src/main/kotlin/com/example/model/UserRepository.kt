package com.example.model

interface UserRepository {
    suspend fun findAll(): List<User>
    suspend fun findById(id: Int): User?
    suspend fun findByUsername(username: String): User?
    suspend fun findByEmail(email: String): User?
    suspend fun findByRole(role: Role): List<User>
    suspend fun addUser(user: User): Unit
    suspend fun updateUser(user: User): Unit
    suspend fun removeUser(id: Int): Boolean
}