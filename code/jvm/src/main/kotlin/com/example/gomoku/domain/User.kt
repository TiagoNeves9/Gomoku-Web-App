package com.example.gomoku.domain

import java.util.*


data class User(val userId: UUID, val username: String, val encodedPassword: String)