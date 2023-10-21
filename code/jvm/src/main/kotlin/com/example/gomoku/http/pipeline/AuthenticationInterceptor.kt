package com.example.gomoku.http.pipeline

import com.example.gomoku.domain.AuthenticatedUser
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AuthenticationInterceptor(
    private val authorizationHeaderProcessor: RequestTokenProcessor
) : HandlerInterceptor {

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        if (handler is HandlerMethod)  {

            val method = handler.method
            val controller = handler.beanType

            if (method.isAnnotationPresent(Authenticated::class.java) || controller.isAnnotationPresent(Authenticated::class.java)) {
                val user = authorizationHeaderProcessor
                    .processAuthorizationHeaderValue(request.getHeader(NAME_AUTHORIZATION_HEADER))
                return if (user == null){
                    response.status = 401
                    response.addHeader(NAME_WWW_AUTHENTICATE_HEADER, RequestTokenProcessor.SCHEME)
                    false
                }else{
                    AuthenticatedUserArgumentResolver.addUserTo(user,request)
                    true
                }
            }
        }
        return true
    }
    companion object {
        const val NAME_AUTHORIZATION_HEADER = "Authorization"
        private const val NAME_WWW_AUTHENTICATE_HEADER = "WWW-Authenticate"
    }
}

