package com.example.gomoku.domain

import java.time.Instant
import java.util.*

data class Game(
    val id: UUID,
    val state: State,
    //val board: Board,
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