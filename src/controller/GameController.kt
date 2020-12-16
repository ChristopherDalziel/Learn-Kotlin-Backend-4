package controller

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.updateMany
import service.GameService

fun Route.gameRoutes(service: GameService) {

    route("/games") {

        get {
            call.respond(HttpStatusCode.OK, service.findAll())
        }

        get("/{id}") {
            val parameters = call.parameters
            val id = parameters.entries().find { it.key == "id" }?.value?.firstOrNull()
            val findGame = service.findOne(id ?: "")
            findGame?.let { game -> call.respond(HttpStatusCode.OK, game) }
        }
    }
}