package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.User
import com.example.gomoku.repository.UsersRepository
import com.example.gomoku.service.Exceptions
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.core.statement.UnableToExecuteStatementException
import java.time.Instant
import java.util.*


class JdbiUsersRepository(private val handle: Handle) : UsersRepository {
    override fun getAll(): List<User> {
        return handle.createQuery(
            "select user_id, username, encoded_password from dbo.users"
        )
            .mapTo<User>()
            .list()
    }

    override fun getById(id: UUID): User =
        handle.createQuery(
            "select * from dbo.users where user_id = :user_id"
        )
            .bind("user_id", id)
            .mapTo<User>()
            .singleOrNull() ?: throw Exceptions.NotFound()

    override fun storeUser(username: String, encodedPassword: String): Int {
        try {
            return handle.createUpdate(
                "insert into dbo.Users (user_id, username, encoded_password) " +
                        "values (:user_id, :username, :password)"
            )
                .bind("user_id", UUID.randomUUID())
                .bind("username", username)
                .bind("password", encodedPassword)
                .execute()
        } catch (ex: UnableToExecuteStatementException) {
            throw Exception() //change to uh exception UsernameAlreadyExists ?
        }
    }

    override fun getUserWithUsername(username: String): User =
        handle.createQuery(
            "select * from dbo.Users where username = :username "
        )
            .bind("username", username)
            .mapTo<User>()
            .singleOrNull() ?: throw Exceptions.NotFound() //change exception?

    override fun getUserWithToken(encodedToken: String): User =
        handle.createQuery(
            """
            select user_id, username, encoded_password
            from dbo.Users as users 
            inner join dbo.Tokens as tokens 
            on users.id = tokens.user_id
            where encoded_password = :given_token
           """
        )
            .bind("given_token", encodedToken)
            .mapTo<User>()
            .singleOrNull() ?: throw Exceptions.NotFound()

    override fun doesUserExist(username: String): Boolean =
        handle.createQuery(
            "select count(*) from dbo.Users where username = :username"
        )
            .bind("username", username)
            .mapTo<Int>()
            .single() == 1

    override fun updateUserToken(userId: UUID, encodedToken: String) {
        TODO("Not yet implemented")
    }

    override fun createToken(token: String, userId: UUID, createdInstant: Instant) {
        try {
            handle.createUpdate(
                "insert into dbo.tokens (encoded_token, generated_at, user_id) " +
                        "VALUES (:token, :instant, :id)"
            )
                .bind("token", token)
                .bind("instant", createdInstant)
                .bind("id", userId)
                .execute()

        } catch (ex: Exception) {
            throw ex
        }
    }

    override fun getUserToken(userID: UUID): String =
        try {
            handle.createQuery(
                " select t.encoded_token from dbo.Tokens t where user_id = :userID "
            )
                .bind("userID", userID)
                .mapTo<String>()
                .single()

        } catch (ex: Exception) {
            throw ex
        }
}