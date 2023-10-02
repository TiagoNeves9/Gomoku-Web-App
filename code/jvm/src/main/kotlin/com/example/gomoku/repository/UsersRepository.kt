package com.example.gomoku.repository

import com.example.gomoku.domain.User
import java.util.*

interface UsersRepository {
    fun getById(id : Int) : User?
    fun insert(name : String)

    fun storeUser(
        username: String,
        encodedPassword : String,
        encodedToken: String
    )

    fun getUserWithUsername(username: String) : User

    fun getUserWithToken(encodedToken: String) : User?

    fun doesUserExist(username: String) : Boolean
    fun updateUserScore(username: String, score: Int) : Boolean

    fun getUserScore(username: String) : Int

    fun getUserNumberOfPlayedGames(username: String) : Int

    fun updateUserToken(userId : UUID, encodedToken: String )
}