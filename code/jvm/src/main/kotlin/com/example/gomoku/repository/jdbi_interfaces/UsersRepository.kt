package com.example.gomoku.repository.jdbi_interfaces

import com.example.gomoku.domain.User
import java.time.Instant
import java.util.*


interface UsersRepository {
    fun getById(id: UUID): User

    fun storeUser(username: String, encodedPassword: String): Int

    fun getUserWithUsername(username: String): User

    fun getUserWithToken(encodedToken: String): User

    fun doesUserExist(username: String): Boolean

    fun updateUserToken(userId: UUID, encodedToken: String)

    fun createToken(token: String, userId: UUID, createdInstant: Instant)
}