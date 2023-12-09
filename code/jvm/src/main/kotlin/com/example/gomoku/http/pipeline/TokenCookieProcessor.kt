package com.example.gomoku.http.pipeline

import com.example.gomoku.domain.AuthenticatedUser
import com.example.gomoku.service.UsersService
import org.springframework.stereotype.Component

@Component
class TokenCookieProcessor(
    private val usersService: UsersService
) {

    fun process(cookieValue: String?) : AuthenticatedUser? {
        if(cookieValue == null) return null

        val user = usersService.getUserByToken(cookieValue)
        return if (user == null) null
        else AuthenticatedUser(user,cookieValue)
    }

}
