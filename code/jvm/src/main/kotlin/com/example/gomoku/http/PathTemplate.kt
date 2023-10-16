package com.example.gomoku.http

object PathTemplate {
    const val HOME = "/"

    const val START = "/games/start"
    const val PLAY = "games/{id}"

    const val USERS = "/users"
    const val USER_BY_ID = "/users/{id}"
    const val CREATE_USER = "/users/signup"
    const val LOGIN = "/users/login"

    const val RANKINGS = "/rankings"
    const val USER_RANKING = "/rankings/{username}"

    const val AUTHOR_INFO = "/authors"
}