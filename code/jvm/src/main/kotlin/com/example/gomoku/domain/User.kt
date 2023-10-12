package com.example.gomoku.domain

import com.example.gomoku.domain.board.Piece
import java.util.UUID


data class User(val userId: UUID, val username: String, val encodedPassword: String, val color: Piece) {
    //TODO color shouldn't be part of the user!
    fun other() =
        if (this.color == Piece.WHITE_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE

    fun piece(): Piece =
        if (this.color == Piece.BLACK_PIECE) Piece.BLACK_PIECE
        else Piece.WHITE_PIECE
}