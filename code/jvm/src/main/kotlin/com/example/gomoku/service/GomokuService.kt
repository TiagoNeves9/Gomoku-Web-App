package com.example.gomoku.service

import com.example.gomoku.domain.Board
import com.example.gomoku.domain.Cells
import com.example.gomoku.domain.Game
import com.example.gomoku.repository.GamesRepository
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException.NotFound
import java.time.Instant
import java.util.UUID


@Component
class GomokuService(private val gamesRepository: GamesRepository) {

    fun play(gameID: UUID, userID : UUID, c : Int, r: Int): Game{
        val game = gamesRepository.getById(gameID)?:throw NotFound()
        val updatedGame  = game.copy(board = game.board.mutate(if (game.playerX.userId == userID) Cells.WHITE else Cells.BLACK, c,r))
        gamesRepository.update(updatedGame)
        return updatedGame
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