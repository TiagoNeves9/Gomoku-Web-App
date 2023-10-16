package com.example.gomoku.domain.game

import java.util.UUID

fun play(game : Game, userID : UUID, col : Int, row : Int) : Game {

    val updatedGame = game.copy()

    val cell = updatedGame.board.get(col,row)

    if(updatedGame.playerB.userId == userID) cell.updateStateToBlackStone()
    else cell.updateStateToWhiteStone()

    updatedGame.board.incPlacedStones()

    if(updatedGame.board.getPlacedStones() >= 9){
        val stonesLine = checkForWin(updatedGame, cell)
        if(stonesLine.isNotEmpty())
            updatedGame.playerWon()
        else
            updatedGame.switchPlayer()
    }
    return updatedGame
}

private fun checkForWin(game: Game, cell: Cell): List<Cell> {

    val horizontalLine = checkForLine(game, cell, Direction.HORIZONTAL)
    if (horizontalLine.isNotEmpty()) return horizontalLine

    val verticalLine = checkForLine(game, cell, Direction.VERTICAL)
    if (verticalLine.isNotEmpty()) return verticalLine

    val diagonalLine = checkForLine(game, cell, Direction.DIAGONAL_RU_LD)
    if (diagonalLine.isNotEmpty()) return diagonalLine

    val diagonalLine2 = checkForLine(game, cell, Direction.DIAGONAL_RD_LU)
    if (diagonalLine.isNotEmpty()) return diagonalLine2

    return emptyList()
}

private fun checkForLine(game: Game, cell: Cell, direction: Direction): List<Cell> {

    val board = game.board.board
    val row = cell.row
    val col = cell.col
    val player = board[row][col].getState()

    val stonesLine = mutableListOf(cell)

    val rowOffset = when (direction) {
        Direction.HORIZONTAL -> 0
        Direction.VERTICAL -> 1
        Direction.DIAGONAL_RU_LD -> 1
        Direction.DIAGONAL_RD_LU -> -1
    }
    val colOffset = when (direction) {
        Direction.HORIZONTAL -> 1
        Direction.VERTICAL -> 0
        Direction.DIAGONAL_RU_LD -> 1
        Direction.DIAGONAL_RD_LU -> 1
    }

    for (i in 1 until 5) {
        val newRow = row + i * rowOffset
        val newCol = col + i * colOffset

        if (newRow in 0 until game.board.size && newCol in 0 until game.board.size &&
            board[newRow][newCol].getState() == player) {
            stonesLine.add(board[newRow][newCol])
        } else {
            break
        }
    }

    for (i in 1 until 5) {
        val newRow = row - i * rowOffset
        val newCol = col - i * colOffset

        if (newRow in 0 until game.board.size && newCol in 0 until game.board.size &&
            board[newRow][newCol].getState() == player) {
            stonesLine.add(board[newRow][newCol])
        } else {
            break
        }
    }

    return if (stonesLine.size == 5) stonesLine else emptyList()
}