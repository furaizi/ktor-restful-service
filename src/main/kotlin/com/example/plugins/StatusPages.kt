package com.example.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respond(
                status = HttpStatusCode.InternalServerError,
                message = ErrorResponse(
                    error = "Internal Server Error",
                    message = cause.localizedMessage ?: "An unexpected error occurred",
                    status = HttpStatusCode.InternalServerError.value
                )
            )
        }
        status(HttpStatusCode.BadRequest) { call, status ->
            call.respond(
                status = status,
                message = ErrorResponse(
                    error = "Bad Request",
                    message = "The request was invalid or cannot be served.",
                    status = status.value
                )
            )
        }
        status(HttpStatusCode.NotFound) { call, status ->
            call.respond(
                status = status,
                message = ErrorResponse(
                    error = "Not Found",
                    message = "The request was invalid or cannot be served.",
                    status = status.value
                )
            )
        }
    }

}

@Serializable
data class ErrorResponse(
    val error: String,
    val message: String,
    val status: Int
)