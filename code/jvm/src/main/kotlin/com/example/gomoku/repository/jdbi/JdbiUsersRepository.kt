package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.User
import com.example.gomoku.repository.UsersRepository


class JdbiUsersRepository : UsersRepository {

    override fun getById(id : Int) : User? {

        return null
    }

    override fun insert(name : String) {

    }

}