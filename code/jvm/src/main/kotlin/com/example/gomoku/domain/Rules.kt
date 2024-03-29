package com.example.gomoku.domain

import com.example.gomoku.domain.board.BoardRun
import com.example.gomoku.domain.board.Cell
import com.example.gomoku.domain.board.distance


data class Rules(val boardDim: Int, val opening: Opening, val variant: Variant)

enum class Opening {
    FREESTYLE, PRO;

    /** This function checks if the opening pro rules are respected */
    fun isProOpening(boardRun: BoardRun, cell: Cell): Boolean {
        if (this == PRO && boardRun.positions.size <= 2) {
            val centralCell = Cell(
                boardRun.boardSize / 2,
                boardRun.boardSize / 2,
                boardRun.boardSize
            )
            // first move must be in the center
            if (boardRun.positions.isEmpty() && cell != centralCell)
                throw IllegalArgumentException(
                    "First move must be in the center of the board! " +
                            "(Coordinates - $centralCell)"
                )
            // second move (first of second player) can be anywhere
            // third move (second of first player) must be at least 3 intersections away
            if (boardRun.positions.size == 2 && cell.distance(centralCell) < 3)
                throw IllegalArgumentException(
                    "Second move (first of second player) must be " +
                            "at least 3 intersections away " +
                            "from the first move (center of the board - $centralCell)! "
                )
        }
        return true
    }
}

enum class Variant {
    FREESTYLE, SWAP_AFTER_FIRST;

    /** This function checks if the variant rules are respected */
    fun wantSwapAfterFirst(boardRun: BoardRun): Boolean {
        return if (this == SWAP_AFTER_FIRST && boardRun.positions.size == 1) {
            println("Do you want to swap? Y/N")
            when (readln().uppercase()) {
                "Y" -> true
                "N" -> false
                else -> throw IllegalArgumentException("Invalid input!")
            }
        } else false
    }
}

fun String.toOpening(): Opening =
    when (this.uppercase()) {
        /** no restrictions */
        "FREESTYLE" -> Opening.FREESTYLE

        /** The first player's first stone must be placed in the center of the board.
         *  The second player's first stone may be placed anywhere on the board.
         *  The first player's second stone must be placed at least three intersections
         *  away from the first stone (two empty intersections in between the two stones).
         *  */
        "PRO" -> Opening.PRO

        /** more openings can be implemented */
        else -> throw Exception()
    }

fun String.toVariant(): Variant =
    when (this.uppercase()) {
        /** no restrictions */
        "FREESTYLE" -> Variant.FREESTYLE

        /** The first player's first stone must be placed in the center of the board.
         *  The second player's first stone may be placed anywhere on the board.
         *  The first player's second stone must be placed at least three intersections
         *  away from the first stone (two empty intersections in between the two stones).
         *  */
        "SWAP_AFTER_FIRST" -> Variant.SWAP_AFTER_FIRST

        /** more variants can be implemented */
        else -> throw Exception()
    }