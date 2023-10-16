package com.example.gomoku.http.model

import java.util.*

data class UserInfoOutputModel(val id: UUID, val username: String)

data class UserOutputModel(val username: String, val token: String)

data class BoardOutputModel(val board: String)

data class GameOutputModel(val id: UUID, val board: BoardOutputModel, val userIdB: UUID, val userIdW: UUID)