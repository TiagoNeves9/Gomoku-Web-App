package com.example.gomoku.domain.game

class Board(
    val size : Int = 15,
    val board: Array<Array<Cell>>,
    private var nPlacedCells : Int = 0
) {
    fun get(c: Int, r: Int) : Cell =
        board[c][r]

    fun incPlacedStones() {
        nPlacedCells+=1
    }

    fun getPlacedStones() : Int {
        return nPlacedCells
    }


    companion object {
        fun create(size : Int?) : Board {
            val boardSize = size ?: 15
            return Board(
                board = Array(boardSize) {
                    Array(boardSize) { it1 -> Cell(Cell.CellState.EMPTY, it, it1)}
                }
            )
        }
    }

}