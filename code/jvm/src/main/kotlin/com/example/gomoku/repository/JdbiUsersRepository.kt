package com.example.gomoku.repository

import com.example.gomoku.domain.User


class JdbiUsersRepository : UsersRepository {

    override fun getById(id : Int) : User? {
        return null
    }

    override fun insert(name : String) {

    }

}