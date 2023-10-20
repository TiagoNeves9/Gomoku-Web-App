package com.example.gomoku.http

import org.springframework.http.ResponseEntity

class Problem{
    companion object{
        fun create(statusCode : Int, ex: Exception) : ResponseEntity<*> =
            ResponseEntity.status(statusCode)
                .header("content-type", "application/problem+json")
                .body(ProblemResponse(statusCode, ex.message))
    }
}

data class ProblemResponse(val statusCode: Int, val message : String?)