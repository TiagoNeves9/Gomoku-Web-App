package com.example.gomoku.repository

import com.example.gomoku.domain.User

interface UsersRepository {
    fun getById(id : Int) : User?
    fun insert(name : String)

    fun storeUser(
        username: String,
        encodedPassword : String
    )

    fun getUserWithUsername(username: String) : User

    fun getUserWithToken(encodedToken: String) : User?

    fun doesUserExist(username: String) : Boolean
    fun updateUserScore(username: String, score: Int) : Boolean


}