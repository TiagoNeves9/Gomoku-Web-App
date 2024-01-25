package com.example.gomoku.domain

import java.time.Instant
import java.util.*


data class Token(val token: String, val userId: UUID, val createdAt: Instant)