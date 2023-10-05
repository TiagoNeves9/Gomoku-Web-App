package com.example.gomoku.http

import com.example.gomoku.http.model.UserInputModel
import com.example.gomoku.http.model.UserOutputModel
import com.example.gomoku.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
class UsersController(private val usersService : UserService) {

    @GetMapping(PathTemplate.USER_BY_ID)
    fun getById(@PathVariable id : UUID) : UserOutputModel {
        val user = usersService.getById(id)
        return UserOutputModel(user.userId,user.username)

    }

    @PostMapping(PathTemplate.CREATE_USER)
    fun insert(@RequestBody user : UserInputModel) {
        TODO("Not yet implemented")
    }

}