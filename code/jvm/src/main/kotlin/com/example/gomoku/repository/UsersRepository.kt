package com.example.gomoku.repository

import com.example.gomoku.domain.User


interface UsersRepository {
    fun getById(id : Int) : User?
    fun insert(name : String)
}

