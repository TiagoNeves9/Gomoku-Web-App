package com.example.gomoku.http.pipeline

import com.example.gomoku.domain.AuthenticatedUser
import com.example.gomoku.service.UsersService
import org.springframework.stereotype.Component

@Component
class AuthorizationHeaderProcessor(
    private val usersService: UsersService
) {

    fun process(authHeader : String?) : AuthenticatedUser? {
        if (authHeader != null && authHeader.startsWith(AuthInterceptor.AUTH_TYPE)) {
            val token = authHeader.substring(7)
            val user = usersService.getUserByToken(token)
            return if (user == null) null
            else AuthenticatedUser(user,token)
        }
        return null
    }

}