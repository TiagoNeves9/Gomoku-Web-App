package com.example.gomoku.http.model

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import java.net.URI


data class ErrorJsonModel(val type: URI, val title: String? = null) {

    companion object {
        val MEDIA_TYPE = MediaType.parseMediaType("application/problem+json")
        val uri = "https://github.com/isel-leic-daw/2023-daw-leic53d-2023-daw-leic53d-g09/tree/main/docs/errors/"
        fun response(status: Int, error: ErrorJsonModel ) = ResponseEntity
            .status(status)
            .header("Content-Type", MEDIA_TYPE.toString())
            .body<Any>(error)

        val userAlreadyExists = ErrorJsonModel(URI(uri+"userAlreadyExists"))
        val insecurePassword = ErrorJsonModel(URI(uri+"insecurePassword"))
        val userOrPasswordAreInvalid = ErrorJsonModel(URI(uri+"userOrPasswordAreInvalid"))
        val invalidRequestContent = ErrorJsonModel(URI(uri+"invalidRequestContent"))

    }
}