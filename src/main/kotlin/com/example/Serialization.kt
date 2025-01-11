package com.example

import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.sql.Connection
import java.sql.DriverManager
import org.jetbrains.exposed.sql.*

fun Application.configureSerialization(repository: UserRepository) {
    routing {
        route("/users") {
            get {
                val users = repository.findAll()
                call.respond(HttpStatusCode.OK, users)
            }
            get("/{id}") {
                val stringId = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val id = stringId.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val user = repository.findById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(HttpStatusCode.OK, user)
            }
            get("byUsername/{username}") {
                val username = call.parameters["username"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val user = repository.findByUsername(username) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(HttpStatusCode.OK, user)
            }
            get("byEmail/{email}") {
                val email = call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val user = repository.findByEmail(email) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(HttpStatusCode.OK, user)
            }
            get("byRole/{role}") {
                val stringRole = call.parameters["role"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val role = Role.valueOf(stringRole) ?: return@get call.respond(HttpStatusCode.BadRequest)
                val user = repository.findByRole(role) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(HttpStatusCode.OK, user)
            }


            post {
                try {
                    val user = call.receive<User>()
                    repository.saveUser(user)
                    call.respond(HttpStatusCode.Created, user)
                }
                catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
                catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            put {
                try {
                    val user = call.receive<User>()
                    if (repository.findById(user.id) == null) {
                        call.respond(HttpStatusCode.NotFound)
                        return@put
                    }

                    repository.saveUser(user)
                    call.respond(HttpStatusCode.NoContent)
                }
                catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
                catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                val stringId = call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)
                val id = stringId.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
                if (repository.removeUser(id))
                    call.respond(HttpStatusCode.NoContent)
                else
                    call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
