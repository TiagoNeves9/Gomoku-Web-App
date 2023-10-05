package com.example.gomoku.http.model

import java.util.UUID

data class UserInputModel(val name : String)

data class UserOutputModel(val id : UUID, val username : String)