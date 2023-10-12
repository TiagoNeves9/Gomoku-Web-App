package com.example.gomoku.domain.board


enum class Piece {
    BLACK_PIECE, WHITE_PIECE;

    fun other() = if (this == WHITE_PIECE) BLACK_PIECE else WHITE_PIECE
}

/*
enum class PieceRep(val symbol: Char) {
    BLACK_PIECE('B'),
    WHITE_SYMBOL('W'),
}
*/