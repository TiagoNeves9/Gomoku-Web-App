package com.example.gomoku.service

import com.example.gomoku.domain.Cells
import com.example.gomoku.domain.Game
import com.example.gomoku.repository.TransactionManager
import org.springframework.stereotype.Component
import java.util.*


@Component
class GomokuService(private val transactionManager: TransactionManager) {

    fun play(gameID: UUID, userID: UUID, c: Int, r: Int): Game {
        return transactionManager.run {
            val game = it.gamesRepository.getById(gameID) ?: throw NotFound()
            val updatedGame = game.copy(
                board = game.board.mutate(
                    if (game.playerB.userId == userID) Cells.BLACK else Cells.WHITE,
                    c,
                    r
                )
            )
            it.gamesRepository.update(updatedGame)
            updatedGame
        }
    }

    fun getById(id: UUID): Game =
        transactionManager.run {
            it.gamesRepository.getById(id) ?: throw NotFound()
        }

    //TODO()
    /*fun start(userXID : UUID, userOID : UUID) : Game{
        val newGame = Game(
            UUID.randomUUID(),
            Game.State.NEXT_PLAYER_X,
            Board.create(),
            Instant.now(),
            Instant.now(),
            userOID,
            userXID,
        )
    }*/
}