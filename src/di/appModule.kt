package di

import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import repository.GameRepository
import service.GameService

val appModule = module {
    single { (uri: String) -> org.litote.kmongo.reactivestreams.KMongo.createClient(uri).coroutine }
    single { (client: CoroutineClient) -> GameRepository(client) }
    single { (repository: GameRepository) -> GameService(repository) }
}