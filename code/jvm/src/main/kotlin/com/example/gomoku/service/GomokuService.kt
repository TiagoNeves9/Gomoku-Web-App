package com.example.gomoku.service

import com.example.gomoku.domain.*
import com.example.gomoku.domain.board.BoardRun
import com.example.gomoku.domain.board.Cell
import com.example.gomoku.domain.board.createBoard
import com.example.gomoku.domain.board.exampleMap
import com.example.gomoku.repository.TransactionManager
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*


@Component
class GomokuService(private val transactionManager: TransactionManager) {
    fun createOrJoinLobby(): Lobby? =
        transactionManager.run {
            it.lobbiesRepository.getLobby()
        }

    fun createLobby(user: User): Lobby =
        transactionManager.run {
            val lobby = Lobby(UUID.randomUUID(), user.userId)
            it.lobbiesRepository.insert(lobby)
            lobby
        }

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

    fun play(gameID: UUID, cell: Cell): Game =
        transactionManager.run {
            val game = it.gamesRepository.getById(gameID)
            val updatedGame = game.computeNewGame(cell)
            it.gamesRepository.update(updatedGame)
            updatedGame
        }

    fun getById(id: UUID): Game =
        transactionManager.run {
            it.gamesRepository.getById(id)
        }

    fun deleteLobby(lobby: Lobby) =
        transactionManager.run {
            it.lobbiesRepository.delete(lobby)
        }
}