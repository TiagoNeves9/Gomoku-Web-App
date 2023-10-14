package com.example.gomoku.domain.board

import com.example.gomoku.domain.Player
import com.example.gomoku.domain.Turn


const val BOARD_DIM = 15
const val N_ON_ROW = 5

sealed class Board(val positions: Map<Cell, Turn>) {
    init {
        check(BOARD_DIM >= N_ON_ROW) { "Board dimension must be >= to $N_ON_ROW" }
    }

    fun addPiece(cell: Cell): BoardRun {
        check(this is BoardRun) { "Game finished." }

        //TODO: Catch do error em vez de throw
        return if (this.positions[cell] != null)
            throw IllegalArgumentException("Square already occupied!")
        else {
            val newMap: Map<Cell, Turn> = this.positions + mapOf(cell to this.turn)
            BoardRun(newMap, this.turn.other())
        }
    }

    /**
     * This function turns Board positions from a Map<Cell, Turn>
     * to a string representation of the board.
     */
    fun positionsToString(): String {
        var str = ""
        positions.forEach {
            str += if (it.value == Turn.BLACK_PIECE) "${it.key}B"
            else "${it.key}W"
        }
        return str
    }

    /**
     * This function turns Board positions from a String
     * to a Map<Cell, Piece>.
     */
    fun stringToPositions(str: String): Map<Cell, Turn> {
        check(str.length % 3 == 0) { "Invalid string length." }
        val map = mutableMapOf<Cell, Turn>()
        var i = 0
        while (i + 2 < str.length) {
            val cell = Cell(str[i].toString().toInt(), str[i + 1].toString().toInt())
            val piece = if (str[i + 2] == 'B') Turn.BLACK_PIECE else Turn.WHITE_PIECE
            map += mapOf(cell to piece)
            i += 3  //each cell-piece pair is represented by 3 chars
        }
        return map
    }

    /**
     * This function turns Board type (run, draw or winner)
     * to a string representation of the board's type.
     */
    fun typeToString(): String {
        return when (this) {
            is BoardRun -> "RUNNING"
            is BoardDraw -> "DRAW"
            is BoardWin -> {
                if (this.winner.second == Turn.BLACK_PIECE) "BLACK_WON"
                else "WHITE_WON"
            }
        }
    }

    /**
     * This functions turna a string representation of the board's type
     * to a Board type (BoardRun, BoardDraw or BoardWin).
     */
    fun stringToType(str: String, lastPlayer: Player): Board {
        return when (str) {
            "RUNNING" -> BoardRun(this.positions, lastPlayer.second)
            "DRAW" -> BoardDraw(this.positions)
            "BLACK_WON" -> BoardWin(this.positions, lastPlayer)
            "WHITE_WON" -> BoardWin(mapOf(), lastPlayer)
            else -> throw IllegalArgumentException("Invalid board type.")
        }
    }
}

class BoardRun(positions: Map<Cell, Turn>, val turn: Turn) : Board(positions) {
    fun checkWin(lastMove: Cell): Boolean =
        positions.size >= 2 * N_ON_ROW - 1 && (
                checkWinInDir(lastMove, Direction.UP, Direction.DOWN) ||
                        checkWinInDir(lastMove, Direction.LEFT, Direction.RIGHT) ||
                        checkWinInDir(lastMove, Direction.UP_LEFT, Direction.DOWN_RIGHT) ||
                        checkWinInDir(lastMove, Direction.UP_RIGHT, Direction.DOWN_LEFT)
                )

    fun checkDraw(): Boolean = positions.size == BOARD_DIM * BOARD_DIM

    private fun checkWinInDir(lastMove: Cell, dir1: Direction, dir2: Direction): Boolean {
        val line =
            cellsInDir(lastMove, dir1).reversed() + lastMove + cellsInDir(lastMove, dir2)
        // we reverse the first part of the list because we want
        // to check the line from left/top to right/bottom
        return checkWinInLine(line)
    }

    private fun checkWinInLine(line: List<Cell>): Boolean {
        var count = 0
        for (cell in line) {
            if (this.positions[cell] == this.turn.other()) {
                count++
                if (count >= N_ON_ROW)
                    return true
            } else count = 0
        }
        return false
    }
}

class BoardWin(positions: Map<Cell, Turn>, val winner: Player) : Board(positions)

class BoardDraw(positions: Map<Cell, Turn>) : Board(positions)

fun createBoard(firstTurn: Turn = Turn.BLACK_PIECE) = BoardRun(mapOf(), firstTurn)

val exampleMap = mapOf(
    "1A".toCell() to Turn.BLACK_PIECE,
    "5C".toCell() to Turn.WHITE_PIECE,
    "2B".toCell() to Turn.BLACK_PIECE,
    "5D".toCell() to Turn.WHITE_PIECE,
)