package com.example.gomoku.domain.board

import com.example.gomoku.domain.Player
import com.example.gomoku.domain.Turn
import com.example.gomoku.service.Exceptions


const val BOARD_DIM = 15
const val BIG_BOARD_DIM = 19
const val N_ON_ROW = 5

sealed class Board(val positions: Map<Cell, Turn>, val boardSize: Int) {
    init {
        check(boardSize >= N_ON_ROW) { "Board dimension must be >= to $N_ON_ROW" }
    }

    fun addPiece(cell: Cell): BoardRun {
        check(this is BoardRun) { "Game finished." }

        return if (cell.rowIndex !in 0 until boardSize || cell.colIndex !in 0 until boardSize)
            throw Exceptions.PlayNotAllowedException("Invalid cell (outside of the board dimensions)!")
        else if (cell.toString() in this.positions.map { it.key.toString() })
            throw Exceptions.WrongPlayException("Square already occupied!")
        else {
            val newMap: Map<Cell, Turn> = this.positions + mapOf(cell to this.turn)
            BoardRun(newMap, this.turn.other(), boardSize)
        }
    }

    /**
     *      Serialize Board positions to a string.
     * This function turns Board positions from a Map<Cell, Turn>
     * to a string representation of the board.
     */
    fun positionsToString(): String {
        var str = ""
        positions.forEach {
            val key = if (it.key.rowIndex < 9) "0${it.key}" else "${it.key}"
            str += if (it.value == Turn.BLACK_PIECE) "${key}B"
            else "${key}W"
        }
        return str
    }

    /**
     *     Serialize Board type to a string.
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
}

/**
 *    Deserialize Board positions from a string.
 * This function turns Board positions from a String
 * to a Map<Cell, Piece>.
 */
fun String.stringToPositions(boardSize: Int): Map<Cell, Turn> {
    check(this.length % 4 == 0) { "Invalid string length." }
    val map = mutableMapOf<Cell, Turn>()
    var i = 0
    while (i + 3 < this.length) {
        val row = (this[i].toString() + this[i + 1].toString())
        val col = this[i + 2].toString()
        val cell = (row + col).toCell(boardSize)
        val piece = if (this[i + 3] == 'B') Turn.BLACK_PIECE else Turn.WHITE_PIECE
        map += mapOf(cell to piece)
        i += 4  //each cell-piece pair is represented by 4 chars
    }
    return map
}

/**
 *    Deserialize Board type from a string.
 * This function turn a string representation of the board's type
 * to a Board type (BoardRun, BoardDraw or BoardWin).
 */
fun String.stringToType(lastBoard: Board, lastPlayer: Player): Board {
    return when (this) {
        "RUNNING" -> BoardRun(lastBoard.positions, lastPlayer.second, lastBoard.boardSize)
        "DRAW" -> BoardDraw(lastBoard.positions, lastBoard.boardSize)
        "BLACK_WON" -> BoardWin(lastBoard.positions, lastPlayer, lastBoard.boardSize)
        "WHITE_WON" -> BoardWin(lastBoard.positions, lastPlayer, lastBoard.boardSize)
        else -> throw IllegalArgumentException("Invalid board type.")
    }
}

class BoardRun(positions: Map<Cell, Turn>, val turn: Turn, boardSize: Int) : Board(positions, boardSize) {
    fun checkWin(lastMove: Cell): Boolean =
        positions.size >= 2 * N_ON_ROW - 1 && (
                checkWinInDir(lastMove, Direction.UP, Direction.DOWN, boardSize) ||
                        checkWinInDir(lastMove, Direction.LEFT, Direction.RIGHT, boardSize) ||
                        checkWinInDir(lastMove, Direction.UP_LEFT, Direction.DOWN_RIGHT, boardSize) ||
                        checkWinInDir(lastMove, Direction.UP_RIGHT, Direction.DOWN_LEFT, boardSize)
                )

    fun checkDraw(boardSize: Int): Boolean = positions.size == boardSize * boardSize

    private fun checkWinInDir(
        lastMove: Cell, dir1: Direction, dir2: Direction, boardSize: Int
    ): Boolean {
        val line =
            cellsInDir(lastMove, dir1, boardSize).reversed() +
                    lastMove +
                    cellsInDir(lastMove, dir2, boardSize)
        /*  we reverse the first part of the list because we want
            to check the line from left/top to right/bottom     */
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

class BoardWin(positions: Map<Cell, Turn>, val winner: Player, boardSize: Int) : Board(positions, boardSize)

class BoardDraw(positions: Map<Cell, Turn>, boardSize: Int) : Board(positions, boardSize)

fun createBoard(firstTurn: Turn = Turn.BLACK_PIECE, boardSize: Int) = BoardRun(mapOf(), firstTurn, boardSize)

/*val exampleMap = mapOf(
    "1A".toCell(BOARD_DIM) to Turn.BLACK_PIECE,
    "5C".toCell(BOARD_DIM) to Turn.WHITE_PIECE,
    "2B".toCell(BOARD_DIM) to Turn.BLACK_PIECE,
    "5D".toCell(BOARD_DIM) to Turn.WHITE_PIECE,
    "11A".toCell(BOARD_DIM) to Turn.BLACK_PIECE,
    "14E".toCell(BOARD_DIM) to Turn.WHITE_PIECE
)*/