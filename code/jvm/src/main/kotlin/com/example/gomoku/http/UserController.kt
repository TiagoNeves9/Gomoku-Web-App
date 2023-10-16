package com.example.gomoku.http

import com.example.gomoku.http.model.UserInfoOutputModel
import com.example.gomoku.http.model.UserInputModel
import com.example.gomoku.http.model.UserOutputModel
import com.example.gomoku.service.UserService
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
class UsersController(private val usersService: UserService) {
    /*
    * TODO:
    *  CHANGE EXCEPTIONS
    *  Status codes
    *  links/actions ? siren?
    * */

    @GetMapping(PathTemplate.USERS)
    fun getAll(): List<UserInfoOutputModel> {
        val list = mutableListOf<UserInfoOutputModel>()
        usersService.getAll().map {
            u -> list.add(UserInfoOutputModel(u.userId,u.username))
        }
        return list
    }


    @GetMapping(PathTemplate.USER_BY_ID)
    fun getById(@PathVariable id: UUID): UserInfoOutputModel {
        val user = usersService.getById(id)
        return UserInfoOutputModel(user.userId, user.username)
    }

    @GetMapping(PathTemplate.USER_BY_USERNAME)
    fun getByUsername(@PathVariable username : String) : UserInfoOutputModel {
        val user = usersService.getByUsername(username)
        return UserInfoOutputModel(user.userId, user.username)
    }

    @PostMapping(PathTemplate.CREATE_USER)
    fun insert(@RequestBody user: UserInputModel): UserOutputModel {
        return try {
            usersService.createNewUser(user.username, user.password)
        } catch (ex: Exception) {
            throw ex
        }
    }

    @PostMapping(PathTemplate.LOGIN)
    fun login(@RequestBody user: UserInputModel): UserOutputModel {
        return try {
            val token = usersService.createToken(user.username, user.password)
            UserOutputModel(user.username, token)
        } catch (ex: Exception) {
            throw ex
        }
    }

}