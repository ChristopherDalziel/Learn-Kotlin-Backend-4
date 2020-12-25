package repository

import models.Game
import org.litote.kmongo.coroutine.CoroutineClient

// Injecting the coroutineClient into our GameRepository
class GameRepository(private val coroutineClient: CoroutineClient) {

//    Database operations

//    These operations are using coroutines in the background

//    Insert our object into our collection
    suspend fun insertGame(request: Game) = collection().insertOne(request)

//    Return all items from our collection and transfer them to a list
    suspend fun findAllGames(): List<Game> =
        collection()
            .find()
            .toList()

    suspend fun findOneGame(id: String): Game? = collection().findOneById(id)

    suspend fun deleteGame(id: String) = collection().deleteOneById(id)

    suspend fun updateGame(requestBody: Game) =
        collection().updateOneById(requestBody._id ?: "", requestBody)

//    Setting up the collection that we want to use (DATABASE/COLLECTION NAMES link to our constants file)
    private fun collection() =
        coroutineClient
            .getDatabase(Constants.DATABASE_NAME)
            .getCollection<Game>(Constants.COLLECTION_NAME)

}