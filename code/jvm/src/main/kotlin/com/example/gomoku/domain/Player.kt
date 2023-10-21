package com.example.gomoku.domain

import java.util.*


/**
 * Player is a Pair of
 * User (person with an account) and Turn (color of the user's pieces)
 * */
typealias Player = Pair<User, Turn>

data class User(val userId: UUID, val username: String, val encodedPassword: String) {
    /*
    fun other() =
        if (this.color == Piece.WHITE_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE

    fun piece(): Piece =
        if (this.color == Piece.BLACK_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE
    */
}

enum class Turn {
    BLACK_PIECE, WHITE_PIECE;

    fun other() =
        if (this == WHITE_PIECE) BLACK_PIECE
        else WHITE_PIECE
}

fun String.toTurn(): Turn =
    if (this == "BLACK_PIECE") Turn.BLACK_PIECE
    else if (this == "WHITE_PIECE") Turn.WHITE_PIECE
    else throw IllegalStateException("Invalid Turn!")