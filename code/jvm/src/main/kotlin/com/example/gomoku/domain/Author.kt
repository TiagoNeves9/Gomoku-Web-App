package com.example.gomoku.domain


data class Author(val number: Int, val name: String, val email: String)

val authors: ArrayList<Author> = arrayListOf(
    Author(48264, "João Pereira", "a48264@alunos.isel.pt"),
    Author(47179, "Afonso Cabaço", "a47179@alunos.isel.pt"),
    Author(48292, "Tiago Neves","a48292@alunos.isel.pt")
)
