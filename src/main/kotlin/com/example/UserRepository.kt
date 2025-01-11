package com.example

interface UserRepository {
    fun findAll(): List<User>
    fun findById(id: Int): User?
    fun findByUsername(username: String): User?
    fun findByEmail(email: String): User?
    fun findByRole(role: Role): List<User>
    fun saveUser(user: User)
    fun removeUser(id: Int): Boolean
}