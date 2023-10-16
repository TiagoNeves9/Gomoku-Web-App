package com.example.gomoku.service

import com.example.gomoku.domain.Token
import com.example.gomoku.domain.User
import com.example.gomoku.http.model.UserOutputModel
import com.example.gomoku.repository.TransactionManager
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID



class WrongUserOrPasswordException(message: String) : Exception(message)
class UserWithIdNotFound(message: String) : Exception(message)

@Component
class UserService(
    var transactionManager: TransactionManager,
    var passwordEncoder: BCryptPasswordEncoder
) {

    fun getAll(): List<User> =
        transactionManager.run { it.usersRepository.getAll() }

    fun getById(id : UUID) = transactionManager.run {
        val isUsernameValid = it.usersRepository.doesUserExist(id)
        if(!isUsernameValid) throw UserWithIdNotFound("User with id $id does not exist!")
        it.usersRepository.getById(id)
    }

    fun createNewUser(username: String, password: String): UserOutputModel {
        val passwordEncoded = passwordEncoder.encode(password)
        return transactionManager.run {
            val storedCount = it.usersRepository.storeUser(username, passwordEncoded)
            if (storedCount == 1) it.usersRepository.getUserWithUsername(username)
            else throw Exceptions.UsernameAlreadyInUseException("Username already in use")
            if (storedCount == 1) {
                val token = UUID.randomUUID().toString() //change this?
                val createdInstant = Instant.now()
                val user = it.usersRepository.getUserWithUsername(username)
                it.usersRepository.createToken(token, user.userId, createdInstant)
                UserOutputModel(username, token)
            }
            else throw Exceptions.UsernameAlreadyInUseException("Username already in use")
        }
    }


    fun createToken(username : String, password : String) : String =
        transactionManager.run {
            val isUsernameValid = it.usersRepository.doesUserExist(username)
            if(!isUsernameValid) throw UsernameNotFoundException("Username $username does not exist!")
            val user = it.usersRepository.getUserWithUsername(username)
            if (!passwordEncoder.matches(password, user.encodedPassword))
                throw Exceptions.WrongUserOrPasswordException("User or Password are incorrect!")
            val token = UUID.randomUUID().toString() //change this?
            val createdInstant = Instant.now()
            it.usersRepository.createToken(token, user.userId, createdInstant)
            token
        }

    fun getByUsername(username: String) : User =
         transactionManager.run {
            val isUsernameValid = it.usersRepository.doesUserExist(username)
            if(!isUsernameValid) throw UsernameNotFoundException("Username $username does not exist!")
            it.usersRepository.getUserWithUsername(username)
        }

    fun getUserByToken(token: String) : User =
         transactionManager.run {
            it.usersRepository.getUserWithToken(token)
        }



    /*AUXILIARY FUNCTIONS*/
    private fun getInstant(): Instant =
        Instant.ofEpochSecond(Instant.now().epochSecond)

    private fun checkTokenValidation(token: Token): Boolean {
        val instant = getInstant()
        val expireDate = token.createdAt.plus(30, ChronoUnit.DAYS)
        return expireDate.isAfter(instant)
    }

}
