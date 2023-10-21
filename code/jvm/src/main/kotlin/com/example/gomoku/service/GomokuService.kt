package com.example.gomoku.service

import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.*
import com.example.gomoku.repository.TransactionManager
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*


@Component
class GomokuService(private val transactionManager: TransactionManager) {
    fun createOrJoinLobby(rules: Rules): Lobby? =
        transactionManager.run {
            it.lobbiesRepository.getLobby(rules)
        }

    fun createLobby(user: User, rules: Rules): Lobby =
        transactionManager.run {
            val lobby = Lobby(UUID.randomUUID(), user.userId, rules)
            it.lobbiesRepository.insert(lobby)
            lobby
        }

    fun getLobbies(): List<Lobby> =
        transactionManager.run {
            it.lobbiesRepository.getAll()
        }

    fun getGames(): List<Game> =
        transactionManager.run {
            it.gamesRepository.getAll()
        }

    // when the other player tries to join the lobby,
    // we create a game and remove the lobby
    fun createGame(lobby: Lobby, host: User, joined: User, score: Int = 100): Game =
        transactionManager.run {
            val hostPlayer = Player(host, Turn.BLACK_PIECE)
            val game = Game(
                lobby.lobbyId, //game ID is the same as lobby ID
                Pair(host, joined),
                createBoard(hostPlayer.second, lobby.rules.boardDim),
                //BoardRun(exampleMap, hostPlayer.second, lobby.rules.boardDim),
                hostPlayer,
                score,
                Instant.now(),
                lobby.rules
            )
            it.gamesRepository.insert(game)
            game
        }

    fun play(gameID: UUID, cell: Cell): Game =
        transactionManager.run {
            val game = it.gamesRepository.getById(gameID)

            val updatedGame = game.computeNewGame(cell)
            if (updatedGame.board is BoardWin) {
                it.statisticsRepository
                    .updateUserRanking(updatedGame.currentPlayer.first.username, updatedGame.score)
                val losingUser =
                    if (updatedGame.currentPlayer.first != updatedGame.users.first)
                        updatedGame.users.first
                    else updatedGame.users.second
                it.statisticsRepository.updateUserRanking(losingUser.username, 0)
            } else if (updatedGame.board is BoardDraw) {
                it.statisticsRepository
                    .updateUserRanking(game.users.first.username, updatedGame.score / 2)
                it.statisticsRepository
                    .updateUserRanking(game.users.second.username, updatedGame.score / 2)
            }
            it.gamesRepository.update(updatedGame)
            updatedGame
        }

    fun getById(id: UUID): Game =
        transactionManager.run {
            it.gamesRepository.getById(id)
        }

    fun isGameCreated(gameId: UUID): Boolean =
        transactionManager.run {
            it.gamesRepository.doesGameExist(gameId)
        }

    fun deleteLobby(lobby: Lobby) =
        transactionManager.run {
            it.lobbiesRepository.delete(lobby)
        }
}