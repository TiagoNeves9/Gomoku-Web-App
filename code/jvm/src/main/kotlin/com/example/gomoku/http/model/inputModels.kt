package com.example.gomoku.http.model

import java.util.*

data class UserInputModel(val username: String, val password: String)

data class GameSetupInputModel(val usernamePB: String, val usernamePW: String)

data class GamePlayInputModel(val userId: UUID, val c: Int, val r: Int)
