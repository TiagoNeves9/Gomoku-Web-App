package com.example.gomoku.http

import com.example.gomoku.domain.User
import com.example.gomoku.http.model.ErrorOutputModel
import com.example.gomoku.http.model.OutputModel
import com.example.gomoku.http.model.UserInputModel
import com.example.gomoku.http.model.UserOutputModel
import com.example.gomoku.service.Exceptions
import com.example.gomoku.service.UsersService
import org.springframework.web.bind.annotation.*
import java.util.UUID


@RestController
class UsersController(private val usersService: UsersService) {
    /*
    * TODO:
    *  CHANGE EXCEPTIONS
    *  Status codes
    *  links/actions ? siren?
    * */

    @GetMapping(PathTemplate.USERS)
    fun getAll(): List<User> = usersService.getAll()

    @GetMapping(PathTemplate.USER_BY_ID)
    fun getById(@PathVariable id: UUID): User = usersService.getById(id)

    /*
    * todo:
    *  refactor
    * we shouldn't call service twice since it's creating a new transaction each time
    * */
    @PostMapping(PathTemplate.CREATE_USER)
    fun insert(@RequestBody user: UserInputModel): SirenModel<OutputModel> {
        return try {
            val createdUser = usersService.createNewUser(user.name, user.password)
            val token = usersService.createToken(user.name, user.password)
            val userModel = UserOutputModel(createdUser.username, createdUser.userId, token)
            siren(userModel) {
                clazz("user signup")
                link(PathTemplate.home(), LinkRelations.HOME)
                link(PathTemplate.start(), LinkRelations.LOBBY)
            }
        } catch (ex: Exceptions.UsernameAlreadyInUseException) {
            siren(ErrorOutputModel(409, ex.message)) {}
        } catch (ex: Exceptions.ErrorCreatingUserException) {
            siren(ErrorOutputModel(400, ex.message)) {}
        }
    }

    @PostMapping(PathTemplate.LOGIN)
    fun login(@RequestBody user: UserInputModel): SirenModel<OutputModel> {
        return try {
            val loggedUser = usersService.getUserCredentials(user.name, user.password)
            val userModel = UserOutputModel(
                loggedUser.username,
                loggedUser.id,
                loggedUser.token
            )
            siren(userModel) {
                clazz("user login")
                link(PathTemplate.home(), LinkRelations.HOME)
                link(PathTemplate.start(), LinkRelations.LOBBY)
            }
        } catch (ex: Exceptions.WrongUserOrPasswordException) {
            siren(ErrorOutputModel(403, ex.message)) {}
        }
    }
}