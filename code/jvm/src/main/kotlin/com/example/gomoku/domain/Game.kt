package com.example.gomoku.domain

import com.example.gomoku.domain.board.Board
import com.example.gomoku.domain.board.BoardDraw
import com.example.gomoku.domain.board.BoardWin
import com.example.gomoku.domain.board.Cell
import java.time.Instant
import java.util.UUID


data class Game(
    val gameId: UUID, val players: Pair<User, User>, val board: Board,
    val currentPlayer: User, val score: Int, val now: Instant
) {
    private fun switchTurn() =
        if (currentPlayer == players.first) players.second
        else players.first

    fun computeNewGame(cell: Cell): Game {
        val newBoard = this.board.addPiece(cell)

        return if (newBoard.checkWin(cell))
            this.copy(board = BoardWin(newBoard.positions, this.currentPlayer))
        else if (newBoard.checkDraw())
            this.copy(board = BoardDraw(newBoard.positions))
        else this.copy(board = newBoard, currentPlayer = this.switchTurn())
    }
}