package com.example.gomoku.service

import com.example.gomoku.repository.UsersRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class UserService(private val usersRepository: UsersRepository) {

    fun getById(id : UUID) = usersRepository.getById(id)?: throw NotFound()

    fun insert(name:String){
        TODO()
    }
}