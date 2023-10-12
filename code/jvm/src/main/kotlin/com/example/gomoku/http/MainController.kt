package com.example.gomoku.http

import com.example.gomoku.domain.authors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class MainController() {
    @GetMapping(PathTemplate.AUTHOR_INFO)
    fun getAuthors(): List<Any> {
        return authors + "Game Version = 0.0.X"
    }
}