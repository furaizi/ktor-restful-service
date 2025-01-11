package com.example

import com.example.model.PostgresUserRepository
import com.example.model.UserRepository
import com.example.plugins.configureDatabases
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import com.example.plugins.configureStatusPages
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    val repository = PostgresUserRepository();

    configureSerialization()
    configureDatabases()
    configureStatusPages()
    configureRouting(repository)
}
