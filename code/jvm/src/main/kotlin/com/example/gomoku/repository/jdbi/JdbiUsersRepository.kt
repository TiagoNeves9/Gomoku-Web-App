package com.example.gomoku.repository.jdbi

import com.example.gomoku.domain.User
import com.example.gomoku.repository.UsersRepository
import com.example.gomoku.service.NotFound
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.kotlin.mapTo
import org.jdbi.v3.core.statement.UnableToExecuteStatementException
import java.util.*


class JdbiUsersRepository(private val handle: Handle) : UsersRepository {

    override fun getById(
        id : UUID
    ) : User? {
        return handle.createQuery(
            "select id, username from dbo.users where id = :user_id"
        )
            .bind("user_id", id)
            .mapTo<User>()
            .singleOrNull()
    }

    override fun storeUser(username: String, encodedPassword: String, encodedToken: String) {
        try {
            handle.createUpdate(
                "insert into dbo.Users (id, username, encoded_password) values (:id, :username, :password)"
                )
                .bind("id", UUID.randomUUID())
                .bind("username", username)
                .bind("password", encodedPassword)
                .execute()
                .toString()
        }catch (ex : UnableToExecuteStatementException) {
            throw Exception() //change to uh exception UsernameAlreadyExists ?
        }
    }

    override fun getUserWithUsername(username: String): User {
        return handle.createQuery(
            "select * from dbo.Users where username = :username "
            )
            .bind("username", username)
            .mapTo<User>()
            .singleOrNull() ?: throw NotFound() //change exception?
    }

    override fun getUserWithToken(encodedToken: String): User? {
        return handle.createQuery(
            """
            select id, username, encoded_password
            from dbo.Users as users 
            inner join dbo.Tokens as tokens 
            on users.id = tokens.user_id
            where encoded_password = :given_token
           """
        )
            .bind("given_token", encodedToken)
            .mapTo<User>()
            .singleOrNull()
    }

    override fun doesUserExist(username: String): Boolean {
        return handle.createQuery(
            "select count(*) from dbo.Users where username = :username"
        )
            .bind("username", username)
            .mapTo<Int>()
            .single() == 1
    }

    override fun updateUserScore(username: String, score: Int): Boolean {
        return handle.createUpdate(
            "update dbo.statistics set score=:score where username = :username"
        )
            .bind("username", username)
            .bind("score", score)
            .execute()
            .compareTo(1) == 0
    }

    override fun getUserScore(username: String): Int {
       return handle.createQuery(
           "select score from dbo.statistics where username = :username"
            )
           .bind("username", username)
           .mapTo<Int>()
           .single()
    }

    override fun getUserNumberOfPlayedGames(username: String): Int {
        return handle.createQuery(
            "select played_games from dbo.statistics where username = :username"
        )
            .bind("username", username)
            .mapTo<Int>()
            .single()
    }

    override fun updateUserToken(userId: UUID, encodedToken: String) {
        TODO("Not yet implemented")
    }

}