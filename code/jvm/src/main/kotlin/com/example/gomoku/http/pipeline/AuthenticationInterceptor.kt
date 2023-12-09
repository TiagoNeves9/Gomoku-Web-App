package com.example.gomoku.http.pipeline

import com.example.gomoku.domain.AuthenticatedUser
import com.example.gomoku.service.UsersService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor


@Component
class AuthInterceptor(
    private val authorizationHeaderProcessor: AuthorizationHeaderProcessor,
    private val tokenCookieProcessor: TokenCookieProcessor
) : HandlerInterceptor {
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
                val aUser = authorizationHeaderProcessor.process(authHeader)
                val cookie = request.cookies?.find { it.name == COOKIE }
                var aUserCookie: AuthenticatedUser? = null
                if (cookie != null)
                    aUserCookie = tokenCookieProcessor.process(cookie.value)
                if (aUser == null && aUserCookie == null) {
                    response.status = 401
                    response.addHeader(
                        NAME_WWW_AUTHENTICATE_HEADER,
                        AUTH_TYPE
                    )
                    return false
                } else if (aUser != null)
                    AuthenticatedUserArgumentResolver.addUserTo(aUser, request)
                else if (aUserCookie != null)
                    AuthenticatedUserArgumentResolver.addUserTo(aUserCookie, request)
            }
        }
        return true // não requer auth
    }

    companion object {
        const val NAME_AUTHORIZATION_HEADER = "Authorization"
        const val AUTH_TYPE = "Bearer"
        const val COOKIE = "Token"
        private const val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"
    }
}