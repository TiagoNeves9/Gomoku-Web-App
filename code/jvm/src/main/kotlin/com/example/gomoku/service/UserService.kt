package com.example.gomoku.service

import com.example.gomoku.domain.Token
import com.example.gomoku.domain.User
import com.example.gomoku.repository.TransactionManager
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.UUID




@Component
class UserService(
    var transactionManager: TransactionManager, var passwordEncoder: BCryptPasswordEncoder
) {
    fun getAll(): List<User> =
        transactionManager.run { it.usersRepository.getAll() }

    fun getById(id: UUID) =
        transactionManager.run { it.usersRepository.getById(id) }

    fun createNewUser(username: String, password: String): User {
        val passwordEncoded = passwordEncoder.encode(password)
        return transactionManager.run {
            val storedCount = it.usersRepository.storeUser(username, passwordEncoded)
            if (storedCount == 1) it.usersRepository.getUserWithUsername(username)
            else throw Exceptions.UsernameAlreadyInUseException("Username already in use")
        }
    }

    fun doesUserExist(username: String): Boolean =
        transactionManager.run {
            it.usersRepository.doesUserExist(username)
        }

    fun createToken(username: String, password: String): String =
        transactionManager.run {
            val user = it.usersRepository.getUserWithUsername(username)
            if (!passwordEncoder.matches(password, user.encodedPassword))
                throw Exceptions.WrongUserOrPasswordException("User or Password are incorrect!")
            val token = UUID.randomUUID().toString() //change this?
            val createdInstant = Instant.now()
            it.usersRepository.createToken(token, user.userId, createdInstant)
            token
        }

    //User?
    fun getByUsername(username: String): User =
        transactionManager.run {
            it.usersRepository.getUserWithUsername(username)
        }

    fun getUserByToken(token: String): User? =
        transactionManager.run {
            null
        }

    /*fun getUserCredentials(name : String, pass: String) = User{
        transactionManager.run {
            val user = it.usersRepository.getUserCredentials(name,pass)
        }
    }*/

    fun getUserToken(userID : UUID ): String =
        transactionManager.run {
            val user = it.usersRepository.getById(userID)
            val token = it.usersRepository.getUserToken(userID)
            token
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