package com.example.model

import com.example.db.UserDAO
import com.example.db.UserTable
import com.example.db.daoToModel
import com.example.db.suspendTransaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.update

class PostgresUserRepository : UserRepository {
    override suspend fun findAll(): List<User> = suspendTransaction {
        UserDAO.all().map(::daoToModel)
    }

    override suspend fun findById(id: Int): User? = suspendTransaction {
        UserDAO
            .findById(id)
            ?.let { daoToModel(it) }
    }

    override suspend fun findByUsername(username: String): User? = suspendTransaction {
        UserDAO
            .find { UserTable.username eq username }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun findByEmail(email: String): User? = suspendTransaction {
        UserDAO
            .find { UserTable.email eq email }
            .limit(1)
            .map(::daoToModel)
            .firstOrNull()
    }

    override suspend fun findByRole(role: Role): List<User> = suspendTransaction {
        UserDAO
            .find { UserTable.role.lowerCase() eq role.name.lowercase() }
            .map(::daoToModel)
    }

    override suspend fun addUser(user: User): Unit = suspendTransaction {
        UserDAO.new {
            username = user.username
            password = user.password
            email = user.email
            role = user.role.name
            isBanned = user.isBanned
        }
    }

    override suspend fun updateUser(user: User): Unit = suspendTransaction {
        UserTable.update({ UserTable.id eq user.id }) {
            it[username] = user.username
            it[password] = user.password
            it[email] = user.email
            it[role] = user.role.name
            it[isBanned] = user.isBanned
        }
    }

    override suspend fun removeUser(id: Int): Boolean = suspendTransaction {
        val rowsDeleted = UserTable
                            .deleteWhere { UserTable.id eq id }
        rowsDeleted == 1
    }

}