package com.example.gomoku.domain

import java.time.Instant
import java.util.*


const val GAME_SIZE = 15

data class Game(
    val id: UUID,
    val state: State,
    val board: Board,
    val created: Instant,
    val updated: Instant,
    val player1: User,
    val player2: User,
) {
    enum class State {
        NEXT_PLAYER_1,
        NEXT_PLAYER_2,
        PLAYER_1_WON,
        PLAYER_2_WON,
        DRAW;

        val finished: Boolean
            get() = this == PLAYER_1_WON || this == PLAYER_2_WON || this == DRAW
    }
}

enum class Cells(val char : Char) {
    EMPTY('+'),
    WHITE('W'),
    BLACK('B');

    companion object {
        fun fromChar(c:Char) = when (c){
            '+' -> EMPTY
            'W' -> WHITE
            'B' -> BLACK
            else -> throw IllegalArgumentException("Invalid value for Board.state")
        }
    }
}

data class Board(private val cells: Array<Array<Cells>>){
    fun get(c:Int, r:Int)= cells[c][r]
    fun mutate(cell : Cells, playCol : Int, playRow: Int):Board{
        val newBoard = Array(GAME_SIZE){
            c->Array(GAME_SIZE){
                r-> cells[c][r]
            }
        }
        newBoard[playCol][playRow]
        return Board(newBoard)
    }
}