package com.example.gomoku.http.pipeline

import com.example.gomoku.domain.AuthenticatedUser
import com.example.gomoku.service.UsersService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor


@Component
class AuthInterceptor(private val userService: UsersService) : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest, response: HttpServletResponse, handler: Any
    ): Boolean {
        if (handler is HandlerMethod) {
            val method = handler.method
            val controller = handler.beanType
            if (
                method.isAnnotationPresent(Authenticated::class.java) ||
                controller.isAnnotationPresent(Authenticated::class.java)
            ) {
                val authHeader = request.getHeader(NAME_AUTHORIZATION_HEADER)
                if (authHeader != null && authHeader.startsWith(AUTH_TYPE)) {
                    val token = authHeader.substring(7)
                    val user = userService.getUserByToken(token)
                    if (user == null) {
                        response.status = 401
                        response.addHeader(
                            NAME_WWW_AUTHENTICATE_HEADER,
                            AUTH_TYPE
                        )
                        return false
                    }
                    val authUser = AuthenticatedUser(user, token)
                    AuthenticatedUserArgumentResolver.addUserTo(authUser, request)
                    return true
                }
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido ou expirado")
                return false
            }
        }
        return true // não requer auth
    }

    companion object {
        const val NAME_AUTHORIZATION_HEADER = "Authorization"
        const val AUTH_TYPE = "Bearer"
        private const val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"
    }
}