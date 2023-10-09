package com.example.gomoku.http

import com.example.gomoku.domain.Author
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController() {

    @GetMapping(PathTemplate.AUTHOR_INFO)
    fun getAuthors(): List<Any> {
        return listOf(
            Author( 47179, "Afonso Cabaço","A47179@alunos.isel.pt"),
            Author( 48264, "João Pereira", "A48264@alunos.isel.pt"),
            Author( 48292, "Tiago Neves","A48292@alunos.isel.pt"),
            "Game Version = 0.0.X"
        )
    }
}