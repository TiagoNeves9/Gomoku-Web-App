package com.example.gomoku.service

import com.example.gomoku.domain.Lobby
import com.example.gomoku.domain.game.Game
import com.example.gomoku.domain.game.play
import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.BoardRun
import com.example.gomoku.domain.board.Cell
import com.example.gomoku.domain.board.createBoard
import com.example.gomoku.domain.board.exampleMap
import com.example.gomoku.repository.TransactionManager
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*


class UserDoesNotExist(message: String) : Exception(message)
class ErrorUpdatingGame(message: String) : Exception(message)

@Component
class GomokuService(private val transactionManager: TransactionManager) {


    fun makePlay(gameID: UUID, userID: UUID, c: Int, r: Int): Game =
        transactionManager.run {
            val game = it.gamesRepository.getById(gameID)

            val updatedGame = play(game, userID, c, r)

            val wasUpdated = it.gamesRepository.update(updatedGame)

            if(wasUpdated) {
                var b = false
                when (updatedGame.state) {
                    Game.GameState.PLAYER_B_WON -> {
                        b = it.statisticsRepository.updateUserRanking(updatedGame.playerB.username,updatedGame.score)
                    }
                    Game.GameState.PLAYER_W_WON -> {
                        b = it.statisticsRepository.updateUserRanking(updatedGame.playerW.username,updatedGame.score)
                    }
                    Game.GameState.DRAW -> {
                        b = it.statisticsRepository.updateUserRanking(updatedGame.playerB.username, updatedGame.score/2)
                        if(b) b = it.statisticsRepository.updateUserRanking(updatedGame.playerW.username, updatedGame.score/2)
                    }
                    Game.GameState.NEXT_PLAYER_B -> {}
                    Game.GameState.NEXT_PLAYER_W -> {}
                }
                if(!b) {
                    it.rollback()
                    throw Exceptions.ErrorUpdatingUserScore("User score not successfully updated")
                }
                updatedGame
            }
            else throw ErrorUpdatingGame("Play was not successful")
        }


    fun getById(id: UUID): Game =
        transactionManager.run {
            it.gamesRepository.getById(id)
        }

    /*
    fun createGame(usernameB : String, usernameW : String) : Game {
        return transactionManager.run {
            if(!it.usersRepository.doesUserExist(usernameB) || !it.usersRepository.doesUserExist(usernameW))
                throw UserDoesNotExist("User does not exist")

            val user1 = it.usersRepository.getUserWithUsername(usernameB)
            val user2 = it.usersRepository.getUserWithUsername(usernameW)
            val game = Game.create(user1, user2)
            it.gamesRepository.insert(game) //todo return insert count -> if 0 throw error
            game
        }
    }*/

    fun isGameCreated(gameId: UUID): Boolean =
        transactionManager.run {
            it.gamesRepository.doesGameExist(gameId)
        }

    /*
    //<-
    // when the other player tries to join the lobby,
    // we create a game and remove the lobby
    fun createGame(host: User, joined: User): Game =
        transactionManager.run {
            val hostPlayer = Player(host, Turn.BLACK_PIECE)
            val game = Game(
                UUID.randomUUID(),
                Pair(host, joined),
                //createBoard(hostPlayer.second),
                BoardRun(exampleMap, hostPlayer.second),
                hostPlayer,
                0,
                Instant.now()
            )
            it.gamesRepository.insert(game)
            game
        }

    fun play(gameID: UUID, userID: UUID, cell: Cell): Game =
        transactionManager.run {
            val game = it.gamesRepository.getById(gameID)
            val updatedGame = game.computeNewGame(cell)
            it.gamesRepository.update(updatedGame)
            updatedGame
        }

     */
}
