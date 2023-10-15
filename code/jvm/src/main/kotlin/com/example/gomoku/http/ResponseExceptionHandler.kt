package com.example.gomoku.http

import com.example.gomoku.http.model.ErrorJsonModel
import com.example.gomoku.service.Exceptions

import org.springframework.beans.TypeMismatchException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ResponseExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler (value = [Exceptions.NotFound::class] )
    fun exceptionHandler() = ResponseEntity
        .status(404)
        .contentType(ErrorJsonModel.MEDIA_TYPE)
        .body(ErrorJsonModel("https://example.org/problems/not-found", "NotFound"))


    override fun handleTypeMismatch(
        ex: TypeMismatchException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {

        logger.info("Handling MethodArgumentNotValidException")
        return ResponseEntity
            .status(404)
            .contentType(ErrorJsonModel.MEDIA_TYPE)
            .body(ErrorJsonModel("https://example.org/problems/not-found", "NotFound"))
    }


}