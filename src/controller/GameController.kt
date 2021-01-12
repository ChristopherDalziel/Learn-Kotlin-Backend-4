package controller

import io.ktor.application.call
import io.ktor.http.HttpStatusCode
import io.ktor.request.*
import io.ktor.response.respond
import io.ktor.routing.*
import models.Game
import service.GameService

// Everything in ktor is a built by functions, here is our route function
fun Route.gameRoutes(service: GameService) {

//    Defining a single path to note repeat ourselves, then define our route functions
    route("/games") {

//        call function that responds with an okay status and calls our GameService
        get {
            call.respond(HttpStatusCode.OK, service.findAll())
        }

        get("/{id}") {
//            call.parameters is a set
            val parameters = call.parameters
            val id = parameters.entries().find { it.key == "id" }?.value?.firstOrNull()
            val findGame = service.findOne(id ?: "")
            findGame?.let { game -> call.respond(HttpStatusCode.OK, game) }
        }

        post<Game>("") { request ->
//            POST request we need to infer a type
            call.respond(HttpStatusCode.Created, service.insertEntity(request))
        }

//        put("") {
////            Return the body and update it of a specific game
//            val requestBody = call.receiveOrNull<Game>()
//            requestBody?.let {
//                call.respond(HttpStatusCode.OK, service.updateEntity(it))
//            }
//        }

        delete("/{id}") {
            val parameters = call.parameters
            val id = parameters.entries().find { it.key == "id" }?.value?.first()
            call.respond(HttpStatusCode.OK, service.deleteEntity(id ?: ""))
        }
    }
}