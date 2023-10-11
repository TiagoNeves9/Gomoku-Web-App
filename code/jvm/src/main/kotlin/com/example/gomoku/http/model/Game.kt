package com.example.gomoku.http.model

import java.util.*


data class GomokuPlayInputModel(val userId: UUID, val c: Int, val r: Int)

data class GomokuStartInputModel(val userIdB: UUID, val userIdW: UUID)

data class BoardOutputModel(val cells: String)

data class GomokuOutputModel(val id: UUID, val board: BoardOutputModel, val userIdB: UUID, val userIdW: UUID)