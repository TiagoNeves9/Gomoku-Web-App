package com.example.gomoku.service

import com.example.gomoku.domain.Lobby
import com.example.gomoku.domain.User
import com.example.gomoku.domain.game.Game
import com.example.gomoku.repository.TransactionManager
import org.springframework.stereotype.Component
import java.util.*

@Component
class LobbyService(
    private val transactionManager: TransactionManager
) {

    fun joinLobbyAndStartGame(userId: UUID) : Game =
        transactionManager.run {
            val lobby = it.lobbiesRepository.getLobby()
            val hostId = lobby.hostUserId

            if(hostId == userId)
                throw Exceptions.MatchmakingErrorException("User cannot match with himself! ")

            val deleted = it.lobbiesRepository.delete(lobby)
            if(!deleted) throw Exceptions.DeleteNotSuccessful("Lobby delete was not successful! ")
            if(!it.usersRepository.doesUserExist(hostId))
                throw Exceptions.NotFound("User with id: $userId not found!")

            val user = it.usersRepository.getById(userId)
            val host = it.usersRepository.getById(hostId)

            val game = Game.create(lobby.lobbyId, user, host)
            val created = it.gamesRepository.insert(game)

            if(!created) {
                it.rollback()
                throw Exceptions.ErrorCreatingGameException("Game not sucessfully created!")
            }
            game
        }


    fun createLobby(userId: UUID): Lobby =
        transactionManager.run {
            if(!it.usersRepository.doesUserExist(userId)) throw Exceptions.NotFound("User with id: $userId not found!")
            val user = it.usersRepository.getById(userId)

            val lobby = Lobby(UUID.randomUUID(), user.userId)
            val wasCreated = it.lobbiesRepository.insert(lobby)
            if(!wasCreated) throw Exceptions.ErrorCreatingLobbyException("Lobby was not successfully created!")

            lobby
        }

    fun doesLobbyExist(): Boolean =
        transactionManager.run {
            it.lobbiesRepository.doesLobbyExist()
        }

}