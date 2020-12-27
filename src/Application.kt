import com.fasterxml.jackson.databind.SerializationFeature
import controller.gameRoutes
import di.appModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.HttpMethod
import io.ktor.jackson.jackson
import io.ktor.routing.routing
import io.ktor.server.engine.commandLineEnvironment
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.core.parameter.parametersOf
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.inject
import org.litote.kmongo.coroutine.CoroutineClient
import repository.GameRepository
import service.GameService

// Using KTOR we need to set up our embeddedServer within our main argument.
fun main(args: Array<String>) {
    embeddedServer(Netty, commandLineEnvironment(args)).start(wait = true)
}

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
// Everything in KTOR is broken up into modules - How we do this is driven by the application.conf folder
fun Application.module() {

//    Every time we want a 'feature' we use install

    install(DefaultHeaders)

//    Used to return JSON format
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

//    Allows us to write less code just writing our define the CORS methods we want to use
    install(CORS) {
        method(HttpMethod.Options)
        method(HttpMethod.Put)
        method(HttpMethod.Delete)
        method(HttpMethod.Patch)
        anyHost()
    }

//    Koin dependency injection (Inside DI -> appModule)
    install(Koin) {
        modules(appModule)
    }

    val uri: String = this.environment.config.property("mongo.uri").getString()

//    Setting up our dependencies for our Coroutine Client (This is the actual KMONGO wrapper library) - Injects into our DB
    val coroutineClient: CoroutineClient by inject {
        parametersOf(uri)
    }

    val repository: GameRepository by inject {
        parametersOf(coroutineClient)
    }

//    Service class for encapsulation - this could be in the repository class, ktor doesn't enforce a particular architecture
    val service: GameService by inject {
        parametersOf(repository)
    }

//    Our routing for HTTP operations, here we've created a function for this
    routing {
        gameRoutes(service)
    }

}