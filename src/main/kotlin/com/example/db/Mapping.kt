package com.example.db

import com.example.model.Role
import com.example.model.User
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UserTable : IntIdTable("users") {
    val username = varchar("username", 255)
    val password = varchar("password", 32)
    val email = varchar("email", 255)
    val role = varchar("role", 35)
    val isBanned = bool("is_banned")
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UserTable)

    var username by UserTable.username
    var password by UserTable.password
    var email by UserTable.email
    var role by UserTable.role
    var isBanned by UserTable.isBanned
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: UserDAO) = User(
    dao.id.value,
    dao.username,
    dao.password,
    dao.email,
    Role.valueOf(dao.role),
    dao.isBanned
)