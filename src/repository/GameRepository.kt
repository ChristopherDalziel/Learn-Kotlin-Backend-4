package repository

import models.Game
import org.litote.kmongo.coroutine.CoroutineClient

class GameRepository(private val coroutineClient: CoroutineClient) {

    suspend fun insertGame(request: Game) = collection().insertOne(request)

    suspend fun findAllGames(): List<Game> =
        collection()
            .find()
            .toList()

    suspend fun findOneGame(id: String): Game? = collection().findOneById(id)

    suspend fun deleteGame(id: String) = collection().deleteOneById(id)

    suspend fun updateGame(requestBody: Game) =
        collection().updateOneById(requestBody._id ?: "", requestBody)

    private fun collection() =
        coroutineClient
            .getDatabase(Constants.DATABASE_NAME)
            .getCollection<Game>(Constants.COLLECTION_NAME)

}