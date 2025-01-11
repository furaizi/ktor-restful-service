package com.example.plugins

import com.example.model.Role
import com.example.model.User
import com.example.model.UserRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(repository: UserRepository) {
    routing {
        route("/users") {
            get {
                val users = repository.findAll()
                call.respond(HttpStatusCode.OK, users)
            }
            get("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respond(HttpStatusCode.BadRequest)
                val user = repository.findById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(HttpStatusCode.OK, user)
            }
            get("/byUsername/{username}") {
                val username = call.parameters["username"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val user = repository.findByUsername(username) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(HttpStatusCode.OK, user)
            }
            get("/byEmail/{email}") {
                val email = call.parameters["email"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val user = repository.findByEmail(email) ?: return@get call.respond(HttpStatusCode.NotFound)
                call.respond(HttpStatusCode.OK, user)
            }
            get("/byRole/{role}") {
                val stringRole = call.parameters["role"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                val role = Role.valueOf(stringRole)
                val user = repository.findByRole(role)
                call.respond(HttpStatusCode.OK, user)
            }


            post {
                try {
                    val user = call.receive<User>()
                    repository.addUser(user)
                    call.respond(HttpStatusCode.Created, user)
                }
                catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
                catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            put("/{id}") {
                try {
                    val id = call.parameters["id"]?.toIntOrNull() ?: return@put call.respond(HttpStatusCode.BadRequest)
                    val user = call.receive<User>()
                    if (id != user.id) {
                        call.respond(HttpStatusCode.BadRequest, "ID in path and body do not match")
                        return@put
                    }
                    if (repository.findById(id) == null) {
                        call.respond(HttpStatusCode.NotFound)
                        return@put
                    }

                    repository.updateUser(user)
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
