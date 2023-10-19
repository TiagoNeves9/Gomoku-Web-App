package com.example.gomoku.http.model

import com.example.gomoku.domain.Game
import com.example.gomoku.domain.board.Board
import com.example.gomoku.domain.board.Cell
import java.util.*


data class GomokuStartInputModel(
    val userId: UUID, val username: String, val encodedPassword: String,
    val boardDim: Int, val opening: String, val variant: String
)

data class GomokuPlayInputModel(val userId: UUID, val board: Board, val cell: Cell)

data class BoardOutputModel(val board: Board)

data class GomokuOutputModel(val game: Game)