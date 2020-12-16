package service

import models.Game
import repository.GameRepository

class GameService(private val repository: GameRepository) {

    suspend fun findAll(): List<Game> = repository.findAllGames()

    suspend fun findOne(id: String): Game? = repository.findOneGame(id)

    suspend fun insertEntity(request: Game) = repository.insertGame(request)

    suspend fun deleteEntity(id: String) = repository.deleteGame(id)

    suspend fun updateEntity(requestBody: Game) = repository.updateGame(requestBody)

}