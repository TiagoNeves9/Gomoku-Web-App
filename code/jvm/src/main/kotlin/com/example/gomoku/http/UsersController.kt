package com.example.gomoku.http

import com.example.gomoku.domain.AuthenticatedUser
import com.example.gomoku.domain.User
import com.example.gomoku.http.model.ErrorOutputModel
import com.example.gomoku.http.model.OutputModel
import com.example.gomoku.http.model.UserInputModel
import com.example.gomoku.http.model.UserOutputModel
import com.example.gomoku.http.pipeline.Authenticated
import com.example.gomoku.http.pipeline.AuthenticatedUserArgumentResolver
import com.example.gomoku.service.Exceptions
import com.example.gomoku.service.UsersService
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest

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

    @Authenticated
    @GetMapping(PathTemplate.USER)
    fun getUser(request: HttpServletRequest): SirenModel<OutputModel> {
        val aUser = request.getAttribute(AuthenticatedUserArgumentResolver.getKey()) as AuthenticatedUser?
            ?: return siren(ErrorOutputModel(401, "User not authenticated! ")) {}

        val user = usersService.getUserByToken(aUser.token)
        return if (user != null) {
            val userModel = UserOutputModel(
                user.username,
                user.userId,
                aUser.token
            )
            siren(userModel) {
                clazz("user login")
                link(PathTemplate.home(), LinkRelations.HOME)
                link(PathTemplate.start(), LinkRelations.LOBBY)
            }
        } else siren(ErrorOutputModel(404, "User not found!")) {}
    }

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
            throw ex
        }
    }

    @GetMapping(PathTemplate.COOKIE)
    fun checkCookie(request: HttpServletRequest): Array<out Cookie>? = request.cookies
}