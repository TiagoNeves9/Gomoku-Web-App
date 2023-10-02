package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.User
import com.example.gomoku.repository.UsersRepository
import java.util.*


class JdbiUsersRepository : UsersRepository {

    override fun getById(id : Int) : User? {
        TODO("Not yet implemented")
        return null
    }

    override fun insert(name : String) {
        TODO("Not yet implemented")
    }

    override fun storeUser(username: String, encodedPassword: String, encodedToken: String) {
        TODO("Not yet implemented")
    }

    override fun storeUser(username: String, encodedPassword: String) {
        TODO("Not yet implemented")
    }

    override fun getUserWithUsername(username: String): User {
        TODO("Not yet implemented")
    }

    override fun getUserWithToken(encodedToken: String): User? {
        TODO("Not yet implemented")
    }

    override fun doesUserExist(username: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun updateUserScore(username: String, score: Int): Boolean {
        TODO("Not yet implemented")
    }

    override fun getUserScore(username: String): Int {
        TODO("Not yet implemented")
    }

    override fun getUserNumberOfPlayedGames(username: String): Int {
        TODO("Not yet implemented")
    }

    override fun updateUserToken(userId: UUID, encodedToken: String) {
        TODO("Not yet implemented")
    }

}