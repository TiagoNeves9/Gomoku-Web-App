package com.example.gomoku.http.model

import java.util.UUID


data class UserInputModel(val name: String, val password: String)

data class UserInfoOutputModel(val id: UUID, val username: String)

data class UserOutputModel(val username: String, val id: UUID, val token: String)