package com.example.gomoku.http

import com.example.gomoku.domain.authors
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


const val SYSTEM_VERSION = "0.0.1"

@RestController
class MainController() {
    //TODO("Home page should have a button to go to START page")
    @GetMapping(PathTemplate.HOME)
    fun home() = "Hello Web"

    @GetMapping(PathTemplate.ABOUT)
    fun getAuthors(): List<Any> = authors + "Game Version = $SYSTEM_VERSION"
}