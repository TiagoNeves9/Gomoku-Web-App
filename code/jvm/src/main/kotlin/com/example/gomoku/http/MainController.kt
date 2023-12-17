package com.example.gomoku.http

import com.example.gomoku.SYSTEM_VERSION
import com.example.gomoku.domain.authors
import com.example.gomoku.http.model.AboutOutputModel
import com.example.gomoku.http.model.AuthorsOutputModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class MainController() {
    @GetMapping(PathTemplate.HOME)
    fun home() = "Hello Web"

    @GetMapping(PathTemplate.AUTHORS)
    fun getAuthors(): ResponseEntity<SirenModel<AuthorsOutputModel>> {
        val authorsModel = AuthorsOutputModel(authors)
        return ResponseEntity.status(200).body(
            siren(authorsModel) { clazz("Authors") }
        )
    }

    @GetMapping(PathTemplate.ABOUT)
    fun getAbout(): ResponseEntity<SirenModel<AboutOutputModel>> {
        val aboutModel = AboutOutputModel(SYSTEM_VERSION)
        return ResponseEntity.status(200).body(
            siren(aboutModel) { clazz("About") }
        )
    }
}