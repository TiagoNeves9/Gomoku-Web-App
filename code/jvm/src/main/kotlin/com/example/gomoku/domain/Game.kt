package com.example.gomoku.domain

import com.example.gomoku.domain.board.Board
import com.example.gomoku.domain.board.BoardDraw
import com.example.gomoku.domain.board.BoardWin
import com.example.gomoku.domain.board.Cell
import java.time.Instant
import java.util.*


data class Game(
    val gameId: UUID, val users: Pair<User, User>, val board: Board,
    val currentPlayer: Player, val score: Int, val now: Instant, val rules: Rules
) {
    private fun switchTurn() =
        if (currentPlayer.first == users.first) users.second
        else users.first

    fun computeNewGame(cell: Cell): Game {
        val newBoard = this.board.addPiece(cell)

        return if (newBoard.checkWin(cell))
            this.copy(board = BoardWin(newBoard.positions, this.currentPlayer, this.rules.boardDim))
        else if (newBoard.checkDraw())
            this.copy(board = BoardDraw(newBoard.positions, this.rules.boardDim))
        else this.copy(
            board = newBoard,
            currentPlayer = Player(this.switchTurn(), this.currentPlayer.second.other())
        )
    }
}