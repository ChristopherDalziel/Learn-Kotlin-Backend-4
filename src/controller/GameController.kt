package controller

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import models.Game
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

        post<Game>("") { request ->
            call.respond(HttpStatusCode.Created, service.insertEntity(request))
        }

        put("") {
            val requestBody = call.receiveOrNull<Game>()
            requestBody?.let {
                call.respond(HttpStatusCode.OK, service.updateEntity(it))
            }
        }

        delete("/{id}") {
            val parameters = call.parameters
            val id = parameters.entries().find { it.key == "id" }?.value?.first()
            call.respond(HttpStatusCode.OK, service.deleteEntity(id ?: ""))
        }
    }
}